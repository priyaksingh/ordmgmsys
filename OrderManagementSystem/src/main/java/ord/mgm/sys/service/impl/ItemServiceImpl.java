/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.entity.Item;
import ord.mgm.sys.mapper.ItemMapper;
import ord.mgm.sys.repository.ItemRepository;
import ord.mgm.sys.service.ItemService;

/**
 * @author priya
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemMapper itemMapper;

	@Override
	public List<ItemDto> getAllItems() {
		logger.info("execute getAllItems() method....");
		Function<Item,ItemDto> mapToItemDto = (item) -> itemMapper.toItemDto(item).get();
		final List<Item> itemsFromDb = (List<Item>) itemRepository.findAll();
		final List<ItemDto> itemsDtoToReturn = itemsFromDb.stream().map(mapToItemDto).collect(Collectors.toList());
		if(itemsDtoToReturn != null && itemsDtoToReturn.size()>0) {
			logger.debug("Successfully retrieved items from the database: {}", itemsDtoToReturn.toString());
		}
		return itemsDtoToReturn;
	}

	@Override
	public Optional<ItemDto> saveItem(ItemDto itemDto) throws IllegalArgumentException {
		logger.info("execute saveItem() method....");
		Optional<ItemDto> savedItemDto = Optional.empty();
		if (itemDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		final Optional<Item> item = itemMapper.toItem(itemDto);
		if (item.isPresent()) {
			final Item savedItemFromDb = itemRepository.save(item.get());
			savedItemDto = itemMapper.toItemDto(savedItemFromDb);
		}
		logger.debug("Successfully saved item to database");
		return savedItemDto;
	}
	
	@Override
	public List<ItemDto> saveItems(List<ItemDto> itemDtoLst) throws IllegalArgumentException {
		logger.info("execute saveItem() method....");
		if (itemDtoLst == null || itemDtoLst.size() == 0) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		Function<ItemDto,Item> mapToItem = (itemDto) -> itemMapper.toItem(itemDto).get();
		List<Item> itemsToInsert = itemDtoLst.stream().map(mapToItem).collect(Collectors.toList());
		final List<Item> savedItemsFrmDb = (List<Item>) itemRepository.saveAll(itemsToInsert);
		Function<Item,ItemDto> mapToItemDto = (item) -> itemMapper.toItemDto(item).get();
		logger.debug("Successfully saved all items to database");
		return (savedItemsFrmDb.stream().map(mapToItemDto).collect(Collectors.toList()));
	}
}
