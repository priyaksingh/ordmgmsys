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
@Table(name="order_detail")
public class OrderDetail {
	
	@Id
	@Column(name = "order_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne(optional = false)
	@JoinColumn(name ="item_id")
	private Item item;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	@Column(name = "item_sub_total")
	private Double itemSubTotal;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(final Order order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(final Item item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(final Integer quantity) {
		this.quantity = quantity;
	}

	public Double getItemSubTotal() {
		return itemSubTotal;
	}

	public void setItemSubTotal(final Double itemSubTotal) {
		this.itemSubTotal = itemSubTotal;
	}

}
