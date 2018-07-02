/**
 * 
 */
package ord.mgm.sys.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ord.mgm.sys.config.OrdMvcAppInitializer;
import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.exception.OrderNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.OrderService;

/**
 * @author priyakrishna
 */
@WebAppConfiguration
@ContextConfiguration(classes = { OrdMvcAppInitializer.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("integrationtest")
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	private MockMvc mockMvc;

	private ObjectMapper mapper;

	private Optional<OrderDto> creatDummyOrderDto() {
		final OrderDto orderDto = new OrderDto();
		orderDto.setCustomerId("priya123");
		orderDto.setOrderConfirmed(true);
		final Optional<OrderDetailDto> orderDetailDto = creatDummyOrderDetailDto();
		final Set<OrderDetailDto> orderDetails = new HashSet<>();
		orderDetails.add(orderDetailDto.get());
		orderDto.setOrderDetails(orderDetails);
		orderDto.setOrderId(Long.valueOf(1111));
		orderDto.setOrderTotalCost(50.0);
		orderDto.setShippingAddressId(Long.valueOf(2222));
		return Optional.of(orderDto);
	}

	private Optional<OrderDetailDto> creatDummyOrderDetailDto() {
		final OrderDetailDto orderDetailDto = new OrderDetailDto();
		orderDetailDto.setItemId(Long.valueOf(1));
		orderDetailDto.setBasketId(Long.valueOf(11));
		orderDetailDto.setItemSubTotal(1.00);
		orderDetailDto.setOrderDetailId(Long.valueOf(111));
		orderDetailDto.setQuantity(2);
		return Optional.of(orderDetailDto);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logger.info("START --- Controller test set up");
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).setControllerAdvice(new ExceptionController())
				.build();
		this.mapper = new ObjectMapper();
		logger.info("END --- Controller test set up");
	}

	@Test
	public void testCreateOrder() throws OrderProcessingException {
		Mockito.when(orderService.createOrder(Mockito.anyString(), Mockito.any(OrderDto.class)))
				.thenReturn(creatDummyOrderDto());
		try {
			final String customerId = "priya123";
			final Optional<OrderDto> orderDto = creatDummyOrderDto();
			final String content = mapper.writeValueAsString(orderDto.get());
			final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/orders/" + customerId)
					.contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();
			Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
			System.out.println("Result from order controller:" + result.getResponse().getContentAsString());
			Assert.assertTrue(result.getResponse().getContentAsString().contains("111"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetAllOrders() throws OrderProcessingException, OrderNotFoundException {
		final Set<OrderDto> orders = new HashSet<>();
		orders.add(creatDummyOrderDto().get());
		Mockito.when(orderService.getAllOrders(Mockito.anyString())).thenReturn(orders);
		try {
			final String customerId = "priya123";
			final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/" + customerId)).andReturn();
			Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
			System.out.println("Result from order controller:" + result.getResponse().getContentAsString());
			Assert.assertTrue(result.getResponse().getContentAsString().contains("priya123"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testGetOrder() throws OrderProcessingException, OrderNotFoundException {
		Mockito.when(orderService.getOrder(Mockito.anyString(), Mockito.anyLong()))
				.thenReturn(creatDummyOrderDto().get());
		try {
			final String customerId = "priya123";
			final String orderId = "1111";
			final MvcResult result = mockMvc
					.perform(MockMvcRequestBuilders.get("/orders/" + customerId + "/" + orderId)).andReturn();
			Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
			System.out.println("Result from basket controller:" + result.getResponse().getContentAsString());
			Assert.assertTrue(result.getResponse().getContentAsString().contains("priya123"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
