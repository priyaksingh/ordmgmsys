/**
 * 
 */
package ord.mgm.sys.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author priya
 *
 */
public class CustomerDto {
	
	private Long id;
	
	private String customerId;
	
	private String customerPwd;
	
	private Set<OrderDto> orders = new HashSet<>();
	
	private Set<ShippingAddressDto> shippingAddresses = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerPwd() {
		return customerPwd;
	}

	public void setCustomerPwd(String customerPwd) {
		this.customerPwd = customerPwd;
	}

	public Set<OrderDto> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderDto> orders) {
		this.orders = orders;
	}

	public Set<ShippingAddressDto> getShippingAddresses() {
		return shippingAddresses;
	}

	public void setShippingAddresses(Set<ShippingAddressDto> shippingAddresses) {
		this.shippingAddresses = shippingAddresses;
	}

	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", customerId=" + customerId + ", customerPwd=" + customerPwd + ", orders="
				+ orders + ", shippingAddresses=" + shippingAddresses + "]";
	}

}
