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
	
	//The order id created is the basket Id
	private Long basketId;
	
	private Long itemId;
	
	private Integer quantity;
	
	private Double itemSubTotal;

	public Long getOrderDetailId() {
		return orderDetailId;
	}

	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}

	public Long getBasketId() {
		return basketId;
	}

	public void setBasketId(Long basketId) {
		this.basketId = basketId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
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
		return "OrderDetailDto [orderDetailId=" + orderDetailId + ", basketId=" + basketId + ", itemId=" + itemId
				+ ", quantity=" + quantity + ", itemSubTotal=" + itemSubTotal + "]";
	}
	
}
