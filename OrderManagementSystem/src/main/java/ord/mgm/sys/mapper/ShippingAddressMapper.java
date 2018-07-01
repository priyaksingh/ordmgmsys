/**
 * 
 */
package ord.mgm.sys.mapper;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.entity.Customer;
import ord.mgm.sys.entity.ShippingAddress;
import ord.mgm.sys.repository.CustomerRepository;

/**
 * @author priya
 *
 */
@Component
@Qualifier("shippingAddrMapper")
public class ShippingAddressMapper implements Mapper<ShippingAddress,ShippingAddressDto>{
	
	private static final Logger logger = LoggerFactory.getLogger(ShippingAddressMapper.class);
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Optional<ShippingAddress> toEntity(ShippingAddressDto shippingAddrDto){
		logger.info("Execute toEntity method....");
		final ShippingAddress shippingAddress = new ShippingAddress();
		if(shippingAddrDto != null) {
			shippingAddress.setShippingAddress(shippingAddrDto.getShippingAddress());
			final Optional<Customer> customer = customerRepository.findByCustomerId(shippingAddrDto.getCustomerId());
			if(customer.isPresent())
			shippingAddress.setCustomer(customer.get());
			/*else
				throw new ShippingAddrProcessException("Customer :"+shippingAddrDto.getCustomerId()+" not found.Cannot map toEntity.");*/
		}
		return Optional.of(shippingAddress);
	}

	@Override
	public Optional<ShippingAddressDto> toDto(ShippingAddress shippingAddr) {
		logger.info("Execute toDto method....");
		final ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
		if(shippingAddr != null) {
			shippingAddressDto.setShippingId(shippingAddr.getShippingId());
			shippingAddressDto.setCustomerId(shippingAddr.getCustomer().getCustomerId());
			shippingAddressDto.setShippingAddress(shippingAddr.getShippingAddress());
		}
		return Optional.of(shippingAddressDto);
	}



}
