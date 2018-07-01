/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ord.mgm.sys.config.UnitTestConfig;
import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.BasketService;
import ord.mgm.sys.service.CustomerService;
import ord.mgm.sys.service.ItemService;
import ord.mgm.sys.service.OrderService;
import ord.mgm.sys.service.ShippingAddressService;

/**
 * @author priya
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("unittest")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderServiceImplTest {

	@Autowired
	private BasketService basketService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	private List<ItemDto> items;

	private CustomerDto customer;

	private ShippingAddressDto shippingAddr;

	private Set<OrderDetailDto> orderDetails;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		items = savedItems();
		customer = createDummyCustomerInDb().get();
		orderDetails = addItemsToBasket();
	}

	@Test
	public void testCreateOrderWithNullObject() {
		try {
			orderService.createOrder(null, null);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testCreateOrderWithCustomerNotInDb() {
		try {
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderDetails(orderDetails);
			orderDto.setCustomerId("priya1234");
			orderDto.setOrderId(orderDetails.iterator().next().getBasketId());
			orderService.createOrder("priya1234", orderDto);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Customer :priya1234 not found.Cannot process order.", expected.getMessage());
		}
	}

	@Test
	public void testCreateOrderWithNoShippingAddress() {
		try {
			OrderDto orderDto = new OrderDto();
			orderDto.setOrderDetails(orderDetails);
			orderDto.setCustomerId("priya123");
			orderDto.setOrderId(orderDetails.iterator().next().getBasketId());
			//This shipping addr does not exists in DB
			orderDto.setShippingAddressId(Long.valueOf(1111111));
			orderService.createOrder("priya123", orderDto);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Shipping address :1111111 not found.Cannot process order.", expected.getMessage());
		}
	}

	@Test
	public void testCreateOrderWithValidData() throws OrderProcessingException {
		shippingAddr = createDummyShippingAddrInDb().get();
		OrderDto orderDto = new OrderDto();
		orderDto.setCustomerId(customer.getCustomerId());
		orderDto.setOrderId(orderDetails.iterator().next().getBasketId());
		orderDto.setOrderDetails(orderDetails);
		orderDto.setShippingAddressId(shippingAddr.getShippingId());
		final OrderDto savedOrderFrmDb = orderService.createOrder("priya123", orderDto).get();
		System.out.println("****Order Placed::"+savedOrderFrmDb);
	}

	private List<ItemDto> savedItems() {
		List<ItemDto> itemDtoLst = new ArrayList<>();
		itemDtoLst.add(createItemDto("Pencil", 0.50));
		itemDtoLst.add(createItemDto("Blue Pen", 2.50));
		itemDtoLst.add(createItemDto("Eraser", 1.50));
		itemDtoLst.add(createItemDto("Notepad", 5.50));
		itemDtoLst.add(createItemDto("Sharpner", 2.50));
		return itemService.saveItems(itemDtoLst);
	}

	private ItemDto createItemDto(final String itemName, final Double itemPrice) {
		final ItemDto itemDto = new ItemDto();
		itemDto.setItemName(itemName);
		itemDto.setItemPrice(itemPrice);
		return itemDto;
	}

	private Optional<CustomerDto> createDummyCustomerInDb() {
		final CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId("priya123");
		customerDto.setCustomerPwd("priya123");
		return customerService.createCustomer(customerDto);
	}

	private Optional<ShippingAddressDto> createDummyShippingAddrInDb() {
		final ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
		shippingAddressDto.setCustomerId(customer.getCustomerId());
		shippingAddressDto.setShippingAddress("Burleigh Gardens, Woking");
		return shippingAddressService.createShippingAddress(shippingAddressDto);
	}

	public Set<OrderDetailDto> addItemsToBasket() throws ItemNotFoundException, OrderProcessingException {
		Set<OrderDetailDto> itemsPushedToBasket = new HashSet<>();
		// 1st item added to basket
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(0).getItemId());
		Integer itemQunatity = 2;
		orderDetailDto.setQuantity(itemQunatity);
		Double itemPrice = itemQunatity * items.get(0).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		Optional<OrderDetailDto> savedOrderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		itemsPushedToBasket.add(savedOrderDetail.get());

		// 2nd item added to basket
		orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(1).getItemId());
		itemQunatity = 3;
		orderDetailDto.setQuantity(itemQunatity);
		itemPrice = itemQunatity * items.get(1).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		orderDetailDto.setBasketId(savedOrderDetail.get().getBasketId());
		savedOrderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		itemsPushedToBasket.add(savedOrderDetail.get());

		// 3rd item added to basket
		orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(2).getItemId());
		itemQunatity = 3;
		orderDetailDto.setQuantity(itemQunatity);
		itemPrice = itemQunatity * items.get(2).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		orderDetailDto.setBasketId(savedOrderDetail.get().getBasketId());
		savedOrderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		itemsPushedToBasket.add(savedOrderDetail.get());

		return itemsPushedToBasket;
	}

}
