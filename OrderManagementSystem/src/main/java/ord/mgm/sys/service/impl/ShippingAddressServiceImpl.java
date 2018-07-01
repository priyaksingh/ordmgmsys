/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ord.mgm.sys.dto.ShippingAddressDto;
import ord.mgm.sys.entity.ShippingAddress;
import ord.mgm.sys.mapper.Mapper;
import ord.mgm.sys.repository.ShippingAddressRepository;
import ord.mgm.sys.service.ShippingAddressService;

/**
 * @author priya
 *
 */
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
	
	private static final Logger logger = LoggerFactory.getLogger(ShippingAddressServiceImpl.class);
	
	@Autowired
	private ShippingAddressRepository shippingAddrRepository;
	
	@Autowired
	@Qualifier("shippingAddrMapper")
	private Mapper<ShippingAddress,ShippingAddressDto> shippingAddrMapper;

	/* (non-Javadoc)
	 * @see ord.mgm.sys.service.ShippingAddressService#createShippingAddress(ord.mgm.sys.dto.ShippingAddressDto)
	 */
	@Override
	@Transactional
	public Optional<ShippingAddressDto> createShippingAddress(final ShippingAddressDto shippingAddrDto) throws IllegalArgumentException{
		logger.info("execute createShippingAddress() method....");
		Optional<ShippingAddressDto> savedShippingAddressDto = Optional.empty();
		if(shippingAddrDto == null) {
			final String errorMsg = "Missing inputs";
			logger.error(errorMsg);
			throw new IllegalArgumentException(errorMsg);
		}
		final Optional<ShippingAddress> shippingAddrEntity = shippingAddrMapper.toEntity(shippingAddrDto);
		if(shippingAddrEntity.isPresent()) {
			final ShippingAddress savedShippingAddrEntityFrmDb = shippingAddrRepository.save(shippingAddrEntity.get());
			savedShippingAddressDto = shippingAddrMapper.toDto(savedShippingAddrEntityFrmDb);
		}
		logger.debug("Successfully saved shipping address to database");
		return savedShippingAddressDto;
	}

}
