/**
 * 
 */
package ord.mgm.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.BasketService;

/**
 * @author priya
 */
@RestController
@RequestMapping("/baskets")
public class BasketController {

	private static final Logger logger = LoggerFactory.getLogger(BasketController.class);

	@Autowired
	private BasketService basketService;

	@RequestMapping(value = { "/{customerId}", "" }, method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public OrderDetailDto addItemToBasket(@PathVariable final String customerId,
			@RequestBody final OrderDetailDto orderDetailDto) throws ItemNotFoundException, OrderProcessingException {
		logger.info("addItemToBasket request received.");
		if (orderDetailDto == null) {
			throw new IllegalArgumentException("Item details not found in the request body.");
		}
		return basketService.addItemToBasket(customerId, orderDetailDto).get();
	}

	@RequestMapping(value = { "/{customerId}/{basketId}/{itemId}",
			"" }, method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public boolean deleteItemFromBasket(@PathVariable final String customerId, @PathVariable final String basketId,
			@PathVariable final String itemId) throws ItemNotFoundException, OrderProcessingException {
		logger.info("deleteItemFromBasket request received.");
		return basketService.deleteItemFromBasket(customerId, Long.valueOf(basketId), Long.valueOf(itemId));
	}

}
