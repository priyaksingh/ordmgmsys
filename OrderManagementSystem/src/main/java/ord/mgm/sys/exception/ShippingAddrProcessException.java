/**
 * 
 */
package ord.mgm.sys.exception;

/**
 * @author priya
 *
 */
public class ShippingAddrProcessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ShippingAddrProcessException(final String errorMsg) {
		super(errorMsg);
	}

	@Override
	public String toString() {
		return "ShippingAddrProcessException [getMessage()=" + getMessage() + "]";
	}

}
