/**
 * 
 */
package ord.mgm.sys.service.impl;

import static org.junit.Assert.*;

import java.util.List;

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
import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.service.BasketService;
import ord.mgm.sys.service.CustomerService;
import ord.mgm.sys.service.ItemService;
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
	private ShippingAddressService shippingAddressService;

	private List<ItemDto> items;

	private CustomerDto customer;

	private ShippingAddressDto shippingAddr;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
