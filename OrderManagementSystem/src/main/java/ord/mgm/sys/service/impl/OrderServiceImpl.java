/**
 * 
 */
package ord.mgm.sys.service.impl;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import ord.mgm.sys.dto.OrderDetailDto;
import ord.mgm.sys.dto.OrderDto;
import ord.mgm.sys.entity.Order;
import ord.mgm.sys.entity.OrderDetail;
import ord.mgm.sys.exception.OrderProcessingException;
import ord.mgm.sys.mapper.Mapper;
import ord.mgm.sys.repository.CustomerRepository;
import ord.mgm.sys.repository.ItemRepository;
import ord.mgm.sys.repository.OrderDetailRepository;
import ord.mgm.sys.repository.OrderRepository;
import ord.mgm.sys.service.OrderService;

/**
 * @author priya
 *
 */
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	@Qualifier("orderDetailMapper")
	private Mapper<OrderDetail, OrderDetailDto> orderDetailMapper;

	@Autowired
	@Qualifier("orderMapper")
	private Mapper<Order, OrderDto> orderMapper;

	/* (non-Javadoc)
	 * @see ord.mgm.sys.service.OrderService#createOrder(java.lang.String, ord.mgm.sys.dto.OrderDto)
	 */
	@Override
	public Optional<OrderDto> createOrder(String customerId, OrderDto orderDto) throws OrderProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ord.mgm.sys.service.OrderService#getAllOrders()
	 */
	@Override
	public Set<OrderDto> getAllOrders() throws OrderProcessingException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ord.mgm.sys.service.OrderService#getOrder(java.lang.String, java.lang.Long)
	 */
	@Override
	public OrderDto getOrder(String customerId, Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
