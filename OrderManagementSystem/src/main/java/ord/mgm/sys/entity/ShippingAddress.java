/**
 * 
 */
package ord.mgm.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author priya
 *
 */
@Entity
@Table(name = "shipping_address")
public class ShippingAddress {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long shippingId;
	
	@Column(name = "shipping_address", nullable = false)
	private String shippingAddress;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(final Long shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(final String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

}
