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
import ord.mgm.sys.service.CustomerService;

/**
 * @author priya
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UnitTestConfig.class, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("unittest")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerServiceImplTest {

	@Autowired
	private CustomerService customerService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testcCreateCustomerWithNullObject() {
		try {
			customerService.createCustomer(null);
			Assert.fail();
		} catch (IllegalArgumentException expected) {
			Assert.assertEquals("Missing inputs", expected.getMessage());
		}
	}

	@Test
	public void testCreateCustomerWithNullData() {
		try {
			final CustomerDto customerDto = new CustomerDto();
			customerDto.setCustomerId(null);
			customerDto.setCustomerPwd(null);
			customerService.createCustomer(customerDto);
			Assert.fail();
		} catch (DataIntegrityViolationException expected) {
			Assert.assertTrue(expected.getMessage().contains("not-null property references a null or transient value"));
		}
	}

	@Test
	public void testCreateCustomerSuccess() {
		final CustomerDto customerDto = new CustomerDto();
		customerDto.setCustomerId("priya123");
		customerDto.setCustomerPwd("priya123");
		final Optional<CustomerDto> createCustomer = customerService.createCustomer(customerDto);
		Assert.assertTrue(createCustomer.isPresent());
		Assert.assertEquals("priya123", createCustomer.get().getCustomerId());
	}

}
