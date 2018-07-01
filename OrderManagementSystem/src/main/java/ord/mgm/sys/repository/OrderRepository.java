/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.Order;

/**
 * @author priya
 *
 */
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	//Get all orders for a customer
	public Set<Order> findByCustomer(final String customerId);

}
