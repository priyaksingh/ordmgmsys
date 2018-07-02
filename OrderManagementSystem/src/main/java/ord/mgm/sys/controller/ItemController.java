/**
 * 
 */
package ord.mgm.sys.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ord.mgm.sys.dto.ItemDto;
import ord.mgm.sys.mapper.CustomerMapper;
import ord.mgm.sys.service.ItemService;

/**
 * @author priya
 */
@RestController
@RequestMapping("/items")
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerMapper.class);

	@Autowired
	private ItemService itemService;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public List<ItemDto> getAllItems() {
		logger.info("getAllItems request received.");
		return itemService.getAllItems();
	}
}
