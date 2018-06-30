/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.Customer;

/**
 * @author priya
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	public Optional<Customer> findByCustomerId(final String customerId);

}
