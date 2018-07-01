/**
 * 
 */
package ord.mgm.sys.service;

import java.util.Optional;

import ord.mgm.sys.dto.CustomerDto;

/**
 * @author priya
 *
 */
public interface CustomerService {
	
	Optional<CustomerDto> createCustomer(final CustomerDto customerDto) throws IllegalArgumentException ;
}
