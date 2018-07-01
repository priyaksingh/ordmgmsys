/**
 * 
 */
package ord.mgm.sys.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author priya
 *
 */
@Entity
@Table(name="order_table")
public class Order {
	
	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@Column(name = "order_number")
	private String orderNumber;
	
	@Column(name = "order_date")
	private Date orderDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	@Column(name = "order_confirmed", nullable = false)
	private boolean orderConfirmed;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
	private Set<OrderDetail> orderDetails = new HashSet<>();
	
	@OneToOne
	@JoinColumn(name = "shipping_id")
	private ShippingAddress shippingAddress;
	
	@Column(name = "order_total_cost")
	private Double orderTotalCost;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(final Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(final String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(final Date orderDate) {
		this.orderDate = orderDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(final Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	public void addOrderDetails(final OrderDetail orderDetail) {
		orderDetail.setOrder(this);
		orderDetails.add(orderDetail);
	}

	public boolean isOrderConfirmed() {
		return orderConfirmed;
	}

	public void setOrderConfirmed(boolean orderConfirmed) {
		this.orderConfirmed = orderConfirmed;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Double getOrderTotalCost() {
		return orderTotalCost;
	}

	public void setOrderTotalCost(Double orderTotalCost) {
		this.orderTotalCost = orderTotalCost;
	}
}
