/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.Order;

/**
 * @author priya
 *
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	public Optional<Order> findByCustomer(final String customerId);
	
	public Optional<Order> findByCustomerIdAndOrderConfirmed(final String customerId, final boolean orderConfirmed);

}
