/**
 * 
 */
package ord.mgm.sys.service;

import java.util.List;
import java.util.Optional;

import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.entity.Item;

/**
 * @author priya
 *
 */
public interface ItemService {

	List<ItemDto> getAllItems();
	
	Optional<ItemDto> saveItem(final ItemDto itemDto) throws IllegalArgumentException;
	
	List<ItemDto> saveItems(List<ItemDto> itemDtoLst) throws IllegalArgumentException;
	
}
