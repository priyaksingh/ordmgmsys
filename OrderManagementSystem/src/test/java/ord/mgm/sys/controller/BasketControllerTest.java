/**
 * 
 */
package ord.mgm.sys.controller;

import java.util.Optional;

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
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.BasketService;

/**
 * @author priyakrishna
 */
@WebAppConfiguration
@ContextConfiguration(classes = { OrdMvcAppInitializer.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("integrationtest")
@RunWith(SpringJUnit4ClassRunner.class)
public class BasketControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(BasketControllerTest.class);

	@Mock
	private BasketService basketService;

	@InjectMocks
	private BasketController basketController;

	private MockMvc mockMvc;

	private ObjectMapper mapper;

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
		this.mockMvc = MockMvcBuilders.standaloneSetup(basketController).setControllerAdvice(new ExceptionController())
				.build();
		this.mapper = new ObjectMapper();
		logger.info("END --- Controller test set up");
	}

	@Test
	public void testAddItemToBasketWithValidData() throws ItemNotFoundException, OrderProcessingException {
		Mockito.when(basketService.addItemToBasket(Mockito.anyString(), Mockito.any(OrderDetailDto.class)))
				.thenReturn(creatDummyOrderDetailDto());
		try {
			final String customerId = "priya123";
			final Optional<OrderDetailDto> orderDetailDto = creatDummyOrderDetailDto();
			final String content = mapper.writeValueAsString(orderDetailDto.get());
			final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/baskets/" + customerId)
					.contentType(MediaType.APPLICATION_JSON).content(content)).andReturn();
			Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
			System.out.println("Result from basket controller:" + result.getResponse().getContentAsString());
			Assert.assertTrue(result.getResponse().getContentAsString().contains("111"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDeleteItemFromBasket() throws Exception {
		final String customerId = "priya123";
		final String basketId = "11";
		final String itemId = "111";
		final MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.delete("/baskets/" + customerId + "/" + basketId + "/" + itemId)
						.contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.NO_CONTENT.value());
	}

}
