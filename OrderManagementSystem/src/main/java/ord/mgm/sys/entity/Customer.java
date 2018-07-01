/**
 * 
 */
package ord.mgm.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author priya
 *
 */
@Entity
@Table(name = "customer")
public class Customer {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "customer_id", nullable = false)
	private String customerId;
	
	@Column(name = "customer_pwd", nullable = false)
	private String customerPwd;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private Set<Order> orders = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
	private Set<ShippingAddress> shippingAddresses = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerPwd() {
		return customerPwd;
	}

	public void setCustomerPwd(final String customerPwd) {
		this.customerPwd = customerPwd;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(final Set<Order> orders) {
		this.orders = orders;
	}

	public Set<ShippingAddress> getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(final Set<ShippingAddress> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}
	
	public void addOrderDetails(final ShippingAddress shippingAddress) {
		shippingAddress.setCustomer(this);
		shippingAddresses.add(shippingAddress);
	}
	
	public void addOrderDetails(final Order order) {
		order.setCustomer(this);
		orders.add(order);
	}

}
