/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.ArrayList;
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

import ord.mgm.sys.UnitTestConfig;
import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.BasketService;
import ord.mgm.sys.service.CustomerService;
import ord.mgm.sys.service.ItemService;
import ord.mgm.sys.service.ShippingAddressService;

/**
 * @author priya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("unittest")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BasketServiceImplTest {

	@Autowired
	private BasketService basketService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ShippingAddressService shippingAddressService;

	private List<ItemDto> items;

	private CustomerDto customer;

	private ShippingAddressDto shippingAddr;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		items = savedItems();
		customer = createDummyCustomerInDb().get();
		shippingAddr = createDummyShippingAddrInDb().get();
	}

	@Test
	public void testAddItemToBasketWithNullObject() {
		try {
			basketService.addItemToBasket(null, null);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testAddItemToBasketWithValidObject() throws ItemNotFoundException, OrderProcessingException {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(0).getItemId());
		final Integer itemQunatity = 2;
		orderDetailDto.setQuantity(itemQunatity);
		final Double itemPrice = itemQunatity * items.get(0).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		Optional<OrderDetailDto> orderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		Assert.assertNotNull(orderDetail);
	}

	@Test
	public void testAdd2ItemToBasketWithValidObject() throws ItemNotFoundException, OrderProcessingException {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(0).getItemId());
		Integer itemQunatity = 2;
		orderDetailDto.setQuantity(itemQunatity);
		Double itemPrice = itemQunatity * items.get(0).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		Optional<OrderDetailDto> orderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		Assert.assertNotNull(orderDetail);

		// 2nd item added to basket
		orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(1).getItemId());
		itemQunatity = 3;
		orderDetailDto.setQuantity(itemQunatity);
		itemPrice = itemQunatity * items.get(1).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		orderDetailDto.setBasketId(orderDetail.get().getBasketId());
		orderDetail = basketService.addItemToBasket("priya123", orderDetailDto);

		Set<OrderDetailDto> orderDetails = basketService.getAllItemsFromBasket("priya123",
				orderDetailDto.getBasketId());

		Assert.assertEquals(2, orderDetails.size());
	}

	@Test
	public void testDeleteItemFromBasketNullObject() {
		try {
			basketService.deleteItemFromBasket(null, null, null);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testDeleteItemFromBasket() throws ItemNotFoundException, OrderProcessingException {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(0).getItemId());
		final Integer itemQunatity = 2;
		orderDetailDto.setQuantity(itemQunatity);
		final Double itemPrice = itemQunatity * items.get(0).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		Optional<OrderDetailDto> orderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		boolean result = basketService.deleteItemFromBasket("priya123", orderDetail.get().getBasketId(),
				orderDetail.get().getItemId());
		Assert.assertTrue(result);
	}

	@Test
	public void getAllItemsFromBasketWithNullObject() {
		try {
			basketService.getAllItemsFromBasket(null, null);
			Assert.fail();
		} catch (Exception expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testAllItemsFromBasketWithValidData() throws ItemNotFoundException, OrderProcessingException {
		OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(items.get(0).getItemId());
		final Integer itemQunatity = 2;
		orderDetailDto.setQuantity(itemQunatity);
		final Double itemPrice = itemQunatity * items.get(0).getItemPrice();
		orderDetailDto.setItemSubTotal(itemPrice);
		Optional<OrderDetailDto> orderDetail = basketService.addItemToBasket("priya123", orderDetailDto);
		Set<OrderDetailDto> result = basketService.getAllItemsFromBasket("priya123", orderDetail.get().getBasketId());
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(orderDetail.get().getQuantity(), result.iterator().next().getQuantity());
		Assert.assertEquals(orderDetail.get().getItemId(), result.iterator().next().getItemId());
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

}
