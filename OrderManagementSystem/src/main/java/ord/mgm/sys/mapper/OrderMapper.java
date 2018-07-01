/**
 * 
 */
package ord.mgm.sys.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.entity.Order;
import ord.mgm.sys.entity.OrderDetail;

/**
 * @author priya
 *
 */
@Component
@Qualifier("orderMapper")
public class OrderMapper implements Mapper<Order,OrderDto>{
	
	private static final Logger logger = LoggerFactory.getLogger(OrderMapper.class);
	
	@Autowired
	@Qualifier("orderDetailMapper")
	private Mapper<OrderDetail,OrderDetailDto> orderDetailMapper;

	@Override
	public Optional<Order> toEntity(final OrderDto orderDto) {
		logger.info("Execute toEntity method....");
		final Order order = new Order();
		if(orderDto != null) {
			if(orderDto.getOrderId() != null) {
				order.setOrderId(orderDto.getOrderId());
			}
			if(orderDto.getOrderNumber() != null)
				order.setOrderNumber(orderDto.getOrderNumber());
			if(orderDto.getOrderDate() != null)
				order.setOrderDate(orderDto.getOrderDate());
			order.setOrderConfirmed(orderDto.isOrderConfirmed());
			final Set<OrderDetailDto> orderDetailsDto = orderDto.getOrderDetails();
			Function<OrderDetailDto,OrderDetail> mapToEntity = (orderDetailDto) -> orderDetailMapper.toEntity(orderDetailDto).get();
			final Set<OrderDetail> orderDetails = orderDetailsDto.stream().map(mapToEntity).collect(Collectors.toSet());
			orderDetails.stream().forEach((orderDetail) -> order.addOrderDetails(orderDetail));
		}
		return Optional.of(order);
	}

	@Override
	public Optional<OrderDto> toDto(Order entity) {
		logger.info("Execute toDto method....");
		final OrderDto orderDto = new OrderDto();
		if(entity != null) {
			orderDto.setOrderId(entity.getOrderId());
			orderDto.setOrderDate(entity.getOrderDate());
			orderDto.setOrderNumber(entity.getOrderNumber());
			orderDto.setOrderConfirmed(entity.isOrderConfirmed());
			orderDto.setOrderTotalCost(entity.getOrderTotalCost());
			Set<OrderDetail> orderDetails = entity.getOrderDetails();
			Function<OrderDetail,OrderDetailDto> mapToDto = (orderDetail) -> orderDetailMapper.toDto(orderDetail).get();
			orderDto.setOrderDetails(orderDetails.stream().map(mapToDto).collect(Collectors.toSet()));
			if(entity.getShippingAddress() != null) {
			orderDto.setShippingAddressId(entity.getShippingAddress().getShippingId());
			}
			orderDto.setCustomerId(entity.getCustomer().getCustomerId());
		}
		return Optional.of(orderDto);
	}

}
