/**
 * 
 */
package ord.mgm.sys.mapper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.entity.Customer;

/**
 * @author priya
 *
 */
@Component
@Qualifier("customerMapper")
public class CustomerMapper implements Mapper<Customer,CustomerDto>{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerMapper.class);

	@Override
	public Optional<Customer> toEntity(CustomerDto customerDto) {
		logger.info("Execute toEntity method....");
		final Customer customer = new Customer();
		if(customerDto != null) {
			customer.setCustomerId(customerDto.getCustomerId());
			customer.setCustomerPwd(customerDto.getCustomerPwd());
		}
		return Optional.of(customer);
	}

	@Override
	public Optional<CustomerDto> toDto(Customer customer) {
		logger.info("Execute toCustomerDto method....");
		final CustomerDto customerDto = new CustomerDto();
		if(customer != null) {
			customerDto.setId(customer.getId());
			customerDto.setCustomerId(customer.getCustomerId());
			customerDto.setCustomerPwd(customer.getCustomerPwd());
		}
		return Optional.of(customerDto);
	}
}
