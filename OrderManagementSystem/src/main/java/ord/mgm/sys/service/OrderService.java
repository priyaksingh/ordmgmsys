/**
 * 
 */
package ord.mgm.sys.service;

import java.util.Optional;
import java.util.Set;

import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.exception.OrderNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;

/**
 * @author priya
 *
 */
public interface OrderService {
	
	public Optional<OrderDto> createOrder(final String customerId, final OrderDto orderDto) throws OrderProcessingException;
	
	public Set<OrderDto> getAllOrders(final String customerId) throws OrderNotFoundException;
	
	public OrderDto getOrder(final String customerId, final Long orderId) throws OrderNotFoundException;

}
