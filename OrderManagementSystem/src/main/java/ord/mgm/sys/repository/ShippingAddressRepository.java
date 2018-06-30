/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.ShippingAddress;

/**
 * @author priya
 *
 */
public interface ShippingAddressRepository extends CrudRepository<ShippingAddress, Long>{
	
	public Set<ShippingAddress> findByCustomer(final String customerId);

}
