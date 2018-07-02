package ord.mgm.sys.controller;

import java.net.ConnectException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.exception.ShippingAddrProcessException;

@ControllerAdvice(basePackages = { "ord.mgm.sys.controller" })
public class ExceptionController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleEntityNotFoundException() {
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static void handleIllegalArgumentException(final IllegalArgumentException e) {
		logger.error("IllegalArgumentException", e);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static void handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
		logger.error("DataIntegrityViolationException", e);
	}

	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static void handleDataIntegrityViolationException(final InvalidDataAccessApiUsageException e) {
		logger.error("InvalidDataAccessApiUsageException", e);
	}

	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public static void handleEntityExistsException(final EntityExistsException e) {
		logger.error("EntityExistsException", e);
	}

	@ExceptionHandler(OrderProcessingException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public static void handleEntityExistsException(final OrderProcessingException e) {
		logger.error("OrderProcessingException", e);
	}

	@ExceptionHandler(ConnectException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public static void handleConnectException(final ConnectException e) {
		logger.error("ConnectException", e);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static void handleConnectException(final OrderNotFoundException e) {
		logger.error("OrderNotFoundException", e);
	}

	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public static void handleConnectException(final ItemNotFoundException e) {
		logger.error("ItemNotFoundException", e);
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public static void handleConnectException(final NullPointerException e) {
		logger.error("NullPointerException", e);
	}

	@ExceptionHandler(ShippingAddrProcessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public static void handleConnectionException(final ShippingAddrProcessException e) {
		logger.error("ShippingAddrProcessException", e);
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static void handleNumberFormatException(final NumberFormatException e) {
		logger.error("NumberFormatException", e);
	}
}
