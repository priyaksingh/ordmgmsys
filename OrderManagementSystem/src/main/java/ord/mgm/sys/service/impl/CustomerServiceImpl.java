/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.entity.Customer;
import ord.mgm.sys.mapper.Mapper;
import ord.mgm.sys.repository.CustomerRepository;
import ord.mgm.sys.service.CustomerService;

/**
 * @author priya
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	@Qualifier("customerMapper")
	private Mapper<Customer,CustomerDto> customerMapper;

	@Override
	@Transactional
	public Optional<CustomerDto> createCustomer(final CustomerDto customerDto) {
		logger.info("execute createCustomer() method....");
		Optional<CustomerDto> savedCustomerDto = Optional.empty();
		if(customerDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		final Optional<Customer> customerEntity = customerMapper.toEntity(customerDto);
		if(customerEntity.isPresent()) {
			final Customer savedCustEntityFrmDb = customerRepository.save(customerEntity.get());
			savedCustomerDto = customerMapper.toDto(savedCustEntityFrmDb);
		}
		logger.debug("Successfully saved customer to database");
		return savedCustomerDto;
	}
}
