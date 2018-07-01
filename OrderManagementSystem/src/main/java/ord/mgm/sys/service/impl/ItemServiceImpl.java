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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.entity.Item;
import ord.mgm.sys.mapper.Mapper;
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
	@Qualifier("itemMapper")
	private Mapper<Item,ItemDto> itemMapper;

	@Override
	public List<ItemDto> getAllItems() {
		logger.info("execute getAllItems() method....");
		Function<Item,ItemDto> mapToItemDto = (item) -> itemMapper.toDto(item).get();
		final List<Item> itemsFromDb = (List<Item>) itemRepository.findAll();
		final List<ItemDto> itemsDtoToReturn = itemsFromDb.stream().map(mapToItemDto).collect(Collectors.toList());
		if(itemsDtoToReturn != null && itemsDtoToReturn.size()>0) {
			logger.debug("Successfully retrieved items from the database: {}", itemsDtoToReturn.toString());
		}
		return itemsDtoToReturn;
	}

	@Override
	@Transactional
	public Optional<ItemDto> saveItem(ItemDto itemDto) throws IllegalArgumentException {
		logger.info("execute saveItem() method....");
		Optional<ItemDto> savedItemDto = Optional.empty();
		if (itemDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		final Optional<Item> item = itemMapper.toEntity(itemDto);
		if (item.isPresent()) {
			final Item savedItemFromDb = itemRepository.save(item.get());
			savedItemDto = itemMapper.toDto(savedItemFromDb);
		}
		logger.debug("Successfully saved item to database");
		return savedItemDto;
	}
	
	@Override
	@Transactional
	public List<ItemDto> saveItems(List<ItemDto> itemDtoLst) throws IllegalArgumentException {
		logger.info("execute saveItem() method....");
		if (itemDtoLst == null || itemDtoLst.size() == 0) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		Function<ItemDto,Item> mapToItem = (itemDto) -> itemMapper.toEntity(itemDto).get();
		List<Item> itemsToInsert = itemDtoLst.stream().map(mapToItem).collect(Collectors.toList());
		final List<Item> savedItemsFrmDb = (List<Item>) itemRepository.saveAll(itemsToInsert);
		Function<Item,ItemDto> mapToItemDto = (item) -> itemMapper.toDto(item).get();
		logger.debug("Successfully saved all items to database");
		return (savedItemsFrmDb.stream().map(mapToItemDto).collect(Collectors.toList()));
	}
}
