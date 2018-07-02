package ord.mgm.sys.controller;

import java.util.Set;

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

import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.exception.OrderNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = { "/{customerId}", "" }, method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public OrderDto createOrder(@PathVariable final String customerId, @RequestBody final OrderDto orderDto)
			throws OrderProcessingException {
		logger.info("createOrder request received.");
		if (orderDto == null) {
			throw new IllegalArgumentException("Order details not found in the request body.");
		}
		return orderService.createOrder(customerId, orderDto).get();
	}

	@RequestMapping(value = { "/{customerId}", "" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Set<OrderDto> getAllOrders(@PathVariable final String customerId) throws OrderNotFoundException {
		logger.info("getAllOrders request received.");
		if (customerId == null || customerId.isEmpty()) {
			throw new IllegalArgumentException("customerId details not found in the request.");
		}
		return orderService.getAllOrders(customerId);
	}

	@RequestMapping(value = { "/{customerId}/{orderId}",
			"" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public OrderDto getOrder(@PathVariable final String customerId, @PathVariable final String orderId)
			throws OrderNotFoundException {
		logger.info("getOrder request received.");
		if (customerId == null || customerId.isEmpty() || orderId == null || orderId.isEmpty()) {
			throw new IllegalArgumentException("customerId/orderId details not found in the request.");
		}
		return orderService.getOrder(customerId, Long.valueOf(orderId));
	}
}
