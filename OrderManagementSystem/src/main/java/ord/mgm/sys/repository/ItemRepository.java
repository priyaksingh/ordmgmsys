/**
 * 
 */
package ord.mgm.sys.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ord.mgm.sys.entity.Item;

/**
 * @author priya
 *
 */
public interface ItemRepository extends CrudRepository<Item, Long> {
	
	public Optional<Item> findByItemName(final String itemName);

}
