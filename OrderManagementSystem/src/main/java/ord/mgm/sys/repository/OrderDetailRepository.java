/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.Item;
import ord.mgm.sys.entity.Order;
import ord.mgm.sys.entity.OrderDetail;

/**
 * @author priya
 *
 */
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
	
	public Optional<OrderDetail> findByOrderAndItem(final Order order, final Item item);
	
	public Set<OrderDetail> findByOrder(final Order order);

}
