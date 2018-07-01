/**
 * 
 */
package ord.mgm.sys.dto;

/**
 * @author priya
 *
 */
public class ShippingAddressDto {
	
	private Long shippingId;
	
	private String shippingAddress;
	
	private String customerId;

	public Long getShippingId() {
		return shippingId;
	}

	public void setShippingId(Long shippingId) {
		this.shippingId = shippingId;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "ShippingAddressDto [shippingId=" + shippingId + ", shippingAddress=" + shippingAddress + ", customerId="
				+ customerId + "]";
	}

}
