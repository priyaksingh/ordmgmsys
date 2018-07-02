/**
 * 
 */
package ord.mgm.sys.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ord.mgm.sys.config.OrdMvcAppInitializer;
import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.service.ItemService;

/**
 * @author priya
 */
@WebAppConfiguration
@ContextConfiguration(classes = { OrdMvcAppInitializer.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("integrationtest")
@RunWith(SpringJUnit4ClassRunner.class)
public class ItemControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(ItemControllerTest.class);

	@Mock
	private ItemService itemService;

	@InjectMocks
	private ItemController itemController;

	private MockMvc mockMvc;

	private ItemDto creatDummyItemDtoObj(Long itemId, String itemName, Double itemPrice) {
		final ItemDto ItemDto = new ItemDto();
		ItemDto.setItemId(itemId);
		ItemDto.setItemName(itemName);
		ItemDto.setItemPrice(itemPrice);
		return ItemDto;
	}

	private List<ItemDto> mockItemDtoList() {
		final List<ItemDto> mockItems = new ArrayList<>();
		mockItems.add(creatDummyItemDtoObj(Long.valueOf(1), "Pencil", 0.50));
		mockItems.add(creatDummyItemDtoObj(Long.valueOf(2), "Pen", 1.50));
		return mockItems;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logger.info("START --- Controller test set up");
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(itemController).setControllerAdvice(new ExceptionController())
				.build();
		logger.info("END --- Controller test set up");
	}

	@Test
	public void testGetAllItems() {
		Mockito.when(itemService.getAllItems()).thenReturn(mockItemDtoList());
		try {
			final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/items/")).andReturn();
			Assert.assertTrue(result.getResponse().getStatus() == HttpStatus.OK.value());
			logger.trace(result.getResponse().getContentAsString());
			Assert.assertTrue(result.getResponse().getContentAsString().contains("Pencil"));
		} catch (Exception e) {
			Assert.fail();
		}
	}

}
