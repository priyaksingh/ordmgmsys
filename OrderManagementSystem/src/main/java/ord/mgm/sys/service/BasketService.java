/**
 * 
 */
package ord.mgm.sys.service;

import java.util.Optional;
import java.util.Set;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.exception.ItemNotFoundException;
import ord.mgm.sys.exception.OrderProcessingException;

/**
 * @author priya
 *
 */
public interface BasketService {
	
	public Optional<OrderDetailDto> addItemToBasket(final String customerId, final OrderDetailDto orderDetailDto) throws ItemNotFoundException, OrderProcessingException;
	
	public Set<OrderDetailDto> getAllItemsFromBasket(final String customerId, final Long basketId) throws OrderProcessingException;
	
	public boolean deleteItemFromBasket(final String customerId, final Long basketId, final  Long itemId) throws OrderProcessingException, ItemNotFoundException;
	
}
