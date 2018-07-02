/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ord.mgm.sys.UnitTestConfig;
import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.service.CustomerService;
import ord.mgm.sys.service.ShippingAddressService;

/**
 * @author priya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("unittest")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ShippingAddressServiceImplTest {

	@Autowired
	private ShippingAddressService shippingAddressService;

	@Autowired
	private CustomerService customerService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCreateShippingAddressWithNullObject() {
		try {
			shippingAddressService.createShippingAddress(null);
			Assert.fail();
		} catch (IllegalArgumentException expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testCreateShippingAddressWithNullData() {
		try {
			final ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
			shippingAddressDto.setCustomerId(null);
			shippingAddressDto.setShippingAddress(null);
			shippingAddressService.createShippingAddress(shippingAddressDto);
			Assert.fail();
		} catch (DataIntegrityViolationException expected) {
			Assert.assertTrue(expected.getMessage().contains("not-null property references a null or transient value"));
		}
	}

	@Test
	public void testCreateShippingAddressWithCustomerNotInDb() {
		try {
			final ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
			shippingAddressDto.setCustomerId("priya1234");
			shippingAddressDto.setShippingAddress("Burleigh Gardens, Woking");
			shippingAddressService.createShippingAddress(shippingAddressDto);
			Assert.fail();
		} catch (DataIntegrityViolationException expected) {
			Assert.assertTrue(expected.getMessage().contains("not-null property references a null or transient value"));
		}
	}

	@Test
	public void testCreateShippingAddressWithValidData() {
		final Optional<CustomerDto> customerDto = createDummyCustomerInDb();
		final ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
		shippingAddressDto.setCustomerId(customerDto.get().getCustomerId());
		shippingAddressDto.setShippingAddress("Burleigh Gardens, Woking");
		Optional<ShippingAddressDto> savedShippingAddrDtoFromDb = shippingAddressService
				.createShippingAddress(shippingAddressDto);
		Assert.assertNotNull(savedShippingAddrDtoFromDb);
		Assert.assertEquals("Burleigh Gardens, Woking", savedShippingAddrDtoFromDb.get().getShippingAddress());
		Assert.assertEquals("priya123", savedShippingAddrDtoFromDb.get().getCustomerId());
	}

	private Optional<CustomerDto> createDummyCustomerInDb() {
		final CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId("priya123");
		customerDto.setCustomerPwd("priya123");
		return customerService.createCustomer(customerDto);
	}

}
