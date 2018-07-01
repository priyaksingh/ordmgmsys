/**
 * 
 */
package ord.mgm.sys.exception;

/**
 * @author priya
 *
 */
public class OrderNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderNotFoundException(final String errorMsg) {
		super(errorMsg);
	}
	
	@Override
	public String toString() {
		return ("OrderNotFoundException occured: " + this.getMessage());
	}

}
