/**
 * 
 */
package ord.mgm.sys.dto;

/**
 * @author priya
 *
 */
public class OrderDetailDto {

	private Long orderDetailId;
	
	private String orderId;
	
	private String itemId;
	
	private Integer quantity;
	
	private Double itemSubTotal;

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getItemSubTotal() {
		return itemSubTotal;
	}

	public void setItemSubTotal(Double itemSubTotal) {
		this.itemSubTotal = itemSubTotal;
	}

	@Override
	public String toString() {
		return "OrderDetailDto [orderDetailId=" + orderDetailId + ", orderId=" + orderId + ", itemId=" + itemId
				+ ", quantity=" + quantity + ", itemSubTotal=" + itemSubTotal + "]";
	}
	
}
