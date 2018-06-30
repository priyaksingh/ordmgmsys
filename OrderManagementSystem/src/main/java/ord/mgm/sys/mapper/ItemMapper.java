/**
 * 
 */
package ord.mgm.sys.mapper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.entity.Item;

/**
 * @author priya
 *
 */
@Component
public class ItemMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemMapper.class);
	
	
	public Optional<Item> toItem(final ItemDto itemDto) {
		logger.info("Execute toItem method....");
		final Item item = new Item();
		if(itemDto != null) {
			item.setItemName(itemDto.getItemName());
			item.setItemPrice(itemDto.getItemPrice());
		}
		return Optional.of(item);
	}
	
	public Optional<ItemDto> toItemDto(final Item item) {
		logger.info("Execute toItemDto method....");
		final ItemDto itemDto = new ItemDto();
		if(item != null) {
			itemDto.setItemId(item.getId());
			itemDto.setItemName(item.getItemName());
			itemDto.setItemPrice(item.getItemPrice());
		}
		return Optional.of(itemDto);
	}

}
