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

import ord.mgm.sys.dto.CustomerDto;
import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.entity.OrderDetail;
import ord.mgm.sys.repository.CustomerRepository;

/**
 * @author priya
 *
 */
@Component
@Qualifier("orderDetailMapper")
public class OrderDetailMapper implements Mapper<OrderDetail,OrderDetailDto>{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderDetailMapper.class);
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Optional<OrderDetail> toEntity(OrderDetailDto orderDetailDto) {
		logger.info("Execute toEntity method....");
		final OrderDetail orderDetail = new OrderDetail();
		if(orderDetailDto != null) {
			orderDetail.setQuantity(orderDetailDto.getQuantity());
			orderDetail.setItemSubTotal(orderDetailDto.getItemSubTotal());
		}
		return Optional.of(orderDetail);
	}

	@Override
	public Optional<OrderDetailDto> toDto(OrderDetail OrderDetailEntity) {
		logger.info("Execute toDto method....");
		final OrderDetailDto orderDetailDto = new OrderDetailDto();
		if(OrderDetailEntity != null) {
			orderDetailDto.setItemId(OrderDetailEntity.getItem().getId());
			orderDetailDto.setOrderDetailId(OrderDetailEntity.getId());
			//this is like a basket id. At a item user can have only one basket
			orderDetailDto.setBasketId(OrderDetailEntity.getOrder().getOrderId());
			orderDetailDto.setItemSubTotal(OrderDetailEntity.getItemSubTotal());
			orderDetailDto.setQuantity(OrderDetailEntity.getQuantity());
		}
		return Optional.of(orderDetailDto);
	}

}
