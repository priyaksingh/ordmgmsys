package ord.mgm.sys.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ord.mgm.sys.entity.OrderDetail;

public class OrderDto {
	
	private Long orderId;
	
	private String orderNumber;
	
	private Date orderDate;
	
	private String customerId;
	
	private boolean orderConfirmed;
	
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	private Double orderCostTotal;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public boolean isOrderConfirmed() {
		return orderConfirmed;
	}

	public void setOrderConfirmed(boolean orderConfirmed) {
		this.orderConfirmed = orderConfirmed;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", orderNumber=" + orderNumber + ", orderDate=" + orderDate
				+ ", customerId=" + customerId + ", orderConfirmed=" + orderConfirmed + ", orderDetails=" + orderDetails
				+ ", orderCostTotal=" + orderCostTotal + "]";
	}

	public Double getOrderCostTotal() {
		return orderCostTotal;
	}

	public void setOrderCostTotal(Double orderCostTotal) {
		this.orderCostTotal = orderCostTotal;
	}

}
