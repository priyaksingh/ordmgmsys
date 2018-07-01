/**
 * 
 */
package ord.mgm.sys.service;

import java.util.Optional;

import ord.mgm.sys.dto.ShippingAddressDto;

/**
 * @author priya
 *
 */
public interface ShippingAddressService {
	
	Optional<ShippingAddressDto> createShippingAddress(final ShippingAddressDto shippingAddr) throws IllegalArgumentException;

}
