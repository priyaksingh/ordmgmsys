/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.entity.Customer;
import ord.mgm.sys.entity.Item;
import ord.mgm.sys.entity.Order;
import ord.mgm.sys.entity.OrderDetail;
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.mapper.Mapper;
import ord.mgm.sys.repository.CustomerRepository;
import ord.mgm.sys.repository.ItemRepository;
import ord.mgm.sys.repository.OrderDetailRepository;
import ord.mgm.sys.repository.OrderRepository;
import ord.mgm.sys.service.BasketService;

/**
 * @author priya
 *
 */
@Service
public class BasketServiceImpl implements BasketService {

	private static final Logger logger = LoggerFactory.getLogger(BasketServiceImpl.class);

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	@Qualifier("orderDetailMapper")
	private Mapper<OrderDetail, OrderDetailDto> orderDetailMapper;

	@Autowired
	@Qualifier("orderMapper")
	private Mapper<Order, OrderDto> orderMapper;

	@Override
	@Transactional
	public Optional<OrderDetailDto> addItemToBasket(final String customerId, final OrderDetailDto orderDetailDto)
			throws OrderProcessingException, ItemNotFoundException {
		logger.info("Execute addItemToBasket method....");
		if (customerId == null || customerId.isEmpty() || orderDetailDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		// 1st check if the Item is in inventory.
		Optional<Item> itemfromDb = itemRepository.findById(orderDetailDto.getItemId());
		if (!itemfromDb.isPresent()) {
			final String errorMsg = "Item not in inventory";
			logger.error(errorMsg);
			throw new ItemNotFoundException(errorMsg);
		}

		// If adding items to new basket
		if (orderDetailDto.getBasketId() == null) {
			return addNewItemToBasket(customerId, orderDetailDto);
		} // If adding items to existing basket
		else {
			return updateItemToBasket(customerId, orderDetailDto);
		}
	}

	private Optional<OrderDetailDto> addNewItemToBasket(final String customerId, final OrderDetailDto orderDetailDto) throws OrderProcessingException {

		OrderDto orderDto = new OrderDto();
		orderDto.setCustomerId(customerId);
		orderDto.setOrderConfirmed(false);

		// 3rd add the item to basket
		Set<OrderDetailDto> orderDetailDtoSet = new HashSet<>();
		orderDetailDtoSet.add(orderDetailDto);
		orderDto.setOrderDetails(orderDetailDtoSet);
		orderDto.setOrderTotalCost(orderDetailDto.getItemSubTotal());

		final Optional<Order> itemToBasket = orderMapper.toEntity(orderDto);

		// 4th get customer details
		final Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent())
			throw new OrderProcessingException("Customer :" + customerId + " not found.Cannot add items to basket.");

		// 5th set the customer details
		itemToBasket.get().setCustomer(customer.get());

		// 6th set the mapping between the item and order
		Optional<Item> itemFrmDb = itemRepository.findById(orderDetailDto.getItemId());
		itemToBasket.get().getOrderDetails().iterator().next().setItem(itemFrmDb.get());

		// 7th persist the basket in db.
		final Order savedOrderFromDb = orderRepository.save(itemToBasket.get());

		return orderDetailMapper.toDto(savedOrderFromDb.getOrderDetails().iterator().next());
	}

	private Optional<OrderDetailDto> updateItemToBasket(final String customerId, final OrderDetailDto orderDetailDto)
			throws OrderProcessingException {
		
		Optional<Order> orderFromDb = orderRepository.findById(orderDetailDto.getBasketId());
		if (!orderFromDb.isPresent()) {
			throw new OrderProcessingException("Error updating items to basket basket.");
		}
		
		//Get all exiting items from basket
		Set<OrderDetail> existingOrderDetails = orderFromDb.get().getOrderDetails();
		//Add new item to existing basket
		existingOrderDetails.add(orderDetailMapper.toEntity(orderDetailDto).get());
		//Update the total order cost
		Double totalOrderCost = existingOrderDetails.stream().mapToDouble(OrderDetail::getItemSubTotal).sum();
		orderFromDb.get().setOrderTotalCost(totalOrderCost);

		// 7th persist the basket in db.
		final Order savedOrderFromDb = orderRepository.save(orderFromDb.get());

		Predicate<OrderDetail> isMatchingItem = (orderDetail) -> orderDetail.getItem().getId().equals(orderDetailDto.getItemId());
		
		return orderDetailMapper.toDto(savedOrderFromDb.getOrderDetails().stream().filter(isMatchingItem).findAny().get());
	}

	@Override
	@Transactional
	public boolean deleteItemFromBasket(String customerId, Long basketId, Long itemId)
			throws OrderProcessingException, ItemNotFoundException {
		logger.info("Execute deleteItemFromBasket method....");
		if (customerId == null || customerId.isEmpty() || basketId == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		// 1st get the item from order_detail table;
		Optional<Item> itemfromDb = itemRepository.findById(itemId);
		if (!itemfromDb.isPresent()) {
			final String errorMsg = "Item not in inventory";
			logger.error(errorMsg);
			throw new ItemNotFoundException(errorMsg);
		}
		// 2nd find the basket
		Optional<Order> orderFromDb = orderRepository.findById(basketId);
		if (!orderFromDb.isPresent()) {
			final String errorMsg = "Basket cannot be found";
			logger.error(errorMsg);
			throw new ItemNotFoundException(errorMsg);
		}

		Optional<OrderDetail> orderDetail = orderDetailRepository.findByOrderAndItem(orderFromDb.get(),
				itemfromDb.get());
		if (!orderDetail.isPresent()) {
			final String errorMsg = "Item not in inventory";
			logger.error(errorMsg);
			throw new ItemNotFoundException(errorMsg);
		}

		logger.info("Item:{} with id:{} found in the basket:{}", itemfromDb.get().getItemName(),
				itemfromDb.get().getId(), basketId);

		// remove single item from basket
		if (orderDetail.get().getQuantity() > 1) {
			logger.debug("Item with multiple quantity found, decrementing the quantity");
			Integer quantity = orderDetail.get().getQuantity();
			orderDetail.get().setQuantity(--quantity);
			orderDetailRepository.save(orderDetail.get());
		} else {
			orderDetailRepository.delete(orderDetail.get());
		}

		return true;
	}

	@Override
	@Transactional
	public Set<OrderDetailDto> getAllItemsFromBasket(final String customerId, final Long basketId)
			throws OrderProcessingException {
		logger.info("Execute deleteItemFromBasket method....");
		if (customerId == null || customerId.isEmpty() || basketId == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		// 1st find the basket
		Optional<Order> orderFromDb = orderRepository.findById(basketId);
		if (!orderFromDb.isPresent()) {
			final String errorMsg = "Basket cannot be found";
			logger.error(errorMsg);
			throw new OrderProcessingException(errorMsg);
		}

		// 2nd get all items from basket
		Set<OrderDetail> orderDetails = orderDetailRepository.findByOrder(orderFromDb.get());
		if (orderDetails == null) {
			final String errorMsg = "Invalid basket";
			logger.error(errorMsg);
			throw new OrderProcessingException(errorMsg);
		}

		Function<OrderDetail, OrderDetailDto> mapToDto = (orderDetail) -> orderDetailMapper.toDto(orderDetail).get();
		return orderDetails.stream().map(mapToDto).collect(Collectors.toSet());
	}

}
