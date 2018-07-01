package ord.mgm.sys.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderDto {
	
	private Long orderId;
	
	private String orderNumber;
	
	private Date orderDate;
	
	private String customerId;
	
	private Long shippingAddressId;
	
	private boolean orderConfirmed;
	
	private Set<OrderDetailDto> orderDetails = new HashSet<>();
	
	private Double orderTotalCost;

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

	public Set<OrderDetailDto> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetailDto> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public Long getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Long shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	
	@Override
	public String toString() {
		return "OrderDto [orderId=" + orderId + ", orderNumber=" + orderNumber + ", orderDate=" + orderDate
				+ ", customerId=" + customerId + ", shippingAddressId=" + shippingAddressId + ", orderConfirmed="
				+ orderConfirmed + ", orderDetails=" + orderDetails + ", orderTotalCost=" + orderTotalCost + "]";
	}

	public Double getOrderTotalCost() {
		return orderTotalCost;
	}

	public void setOrderTotalCost(Double orderTotalCost) {
		this.orderTotalCost = orderTotalCost;
	}

}
