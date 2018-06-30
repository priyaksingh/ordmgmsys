/**
 * 
 */
package ord.mgm.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author priya
 *
 */
@Entity
@Table(name="item")
public class Item {

	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "item_name", nullable = false)
	private String itemName;
	
	@Column(name ="item_price", nullable = false)
	private Double itemPrice;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(final String itemName) {
		this.itemName = itemName;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(final Double itemPrice) {
		this.itemPrice = itemPrice;
	}
}
