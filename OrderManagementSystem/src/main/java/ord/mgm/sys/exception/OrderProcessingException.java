/**
 * 
 */
package ord.mgm.sys.exception;

/**
 * @author priya
 *
 */
public class OrderProcessingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public OrderProcessingException(final String errorMsg) {
		super(errorMsg);
	}
	
	@Override
	public String toString() {
		return ("OrderProcessingException occured: " + this.getMessage());
	}

}
