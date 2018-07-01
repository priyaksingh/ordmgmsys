/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.entity.Customer;
import ord.mgm.sys.entity.Order;
import ord.mgm.sys.entity.OrderDetail;
import ord.mgm.sys.entity.ShippingAddress;
import ord.mgm.sys.exception.OrderNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.mapper.Mapper;
import ord.mgm.sys.repository.CustomerRepository;
import ord.mgm.sys.repository.OrderRepository;
import ord.mgm.sys.repository.ShippingAddressRepository;
import ord.mgm.sys.service.OrderService;

/**
 * @author priya
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ShippingAddressRepository shippingAddressRepository;

	@Autowired
	@Qualifier("orderDetailMapper")
	private Mapper<OrderDetail, OrderDetailDto> orderDetailMapper;

	@Autowired
	@Qualifier("orderMapper")
	private Mapper<Order, OrderDto> orderMapper;


	/*
	 * (non-Javadoc)
	 * 
	 * @see ord.mgm.sys.service.OrderService#createOrder(java.lang.String,
	 * ord.mgm.sys.dto.OrderDto)
	 */
	@Override
	@Transactional
	public Optional<OrderDto> createOrder(String customerId, OrderDto orderDto) throws OrderProcessingException {
		logger.info("Execute createOrder method....");
		if (customerId == null || customerId.isEmpty() || orderDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}

		// 1st find the order from Db
		Optional<Order> fetchedOrderEntityFromDb = orderRepository.findById(orderDto.getOrderId());
		if (!fetchedOrderEntityFromDb.isPresent()) {
			throw new OrderProcessingException("Order not found in db.Cannot process order.");
		}

		// 2nd get customer details
		final Optional<Customer> customer = customerRepository.findByCustomerId(customerId);
		if (!customer.isPresent())
			throw new OrderProcessingException("Customer :" + customerId + " not found.Cannot process order.");

		// 3rd get Shipping details
		final Optional<ShippingAddress> shippingAddress = shippingAddressRepository
				.findById(orderDto.getShippingAddressId());
		if (!shippingAddress.isPresent())
			throw new OrderProcessingException(
					"Shipping address :" + orderDto.getShippingAddressId() + " not found.Cannot process order.");

		// 3rd check there are items to order
		if (orderDto.getOrderDetails().size() == 0) {
			throw new OrderProcessingException("There are no items to order.Cannot process order request.");
		}
		
		Order orderToUpdate = fetchedOrderEntityFromDb.get();
		
		orderToUpdate.setOrderId(orderDto.getOrderId());
		orderToUpdate.setOrderDate(new Date());
		orderToUpdate.setOrderNumber(generateOrderNumber());
		orderToUpdate.setOrderConfirmed(true);
		orderToUpdate.setOrderTotalCost(orderDto.getOrderDetails().stream().mapToDouble(OrderDetailDto::getItemSubTotal).sum());
		orderToUpdate.setCustomer(fetchedOrderEntityFromDb.get().getCustomer());
		orderToUpdate.setShippingAddress(shippingAddress.get());

		Order savedOrderEntityFromDb = orderRepository.save(orderToUpdate);

		logger.info("Successfully placed order");

		return orderMapper.toDto(savedOrderEntityFromDb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ord.mgm.sys.service.OrderService#getAllOrders()
	 */
	@Override
	@Transactional
	public Set<OrderDto> getAllOrders(final String customerId) throws OrderNotFoundException {
		logger.info("Execute getAllOrders method....");
		if (customerId == null || customerId.isEmpty()) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		Optional<Customer> customerFrmDb = customerRepository.findByCustomerId(customerId);
		if(!customerFrmDb.isPresent()) {
			throw new OrderNotFoundException("Order not found for customer : "+customerId) ;
		}
		Set<Order> allOrdersFromCustomer = orderRepository.findByCustomer(customerFrmDb.get());
		
		Set<OrderDto> orders = new HashSet<>();
		for(Order order : allOrdersFromCustomer) {
			orders.add(orderMapper.toDto(order).get());
		}
		
		return orders;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ord.mgm.sys.service.OrderService#getOrder(java.lang.String,
	 * java.lang.Long)
	 */
	@Override
	@Transactional
	public OrderDto getOrder(String customerId, Long orderId) throws OrderNotFoundException {
		logger.info("Execute getOrder method....");
		if (customerId == null || customerId.isEmpty() || orderId == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		
		// 1st find the order from Db
		Optional<Order> fetchedOrderEntityFromDb = orderRepository.findById(orderId);
		
		if(!fetchedOrderEntityFromDb.isPresent()) {
			throw new OrderNotFoundException("Order not found.");
		}
		
		logger.info("Found the order with id:{}", fetchedOrderEntityFromDb.get().getOrderId());
		return orderMapper.toDto(fetchedOrderEntityFromDb.get()).get();
	}

	private String generateOrderNumber() {
		return UUID.randomUUID().toString();
	}

}
