/**
 * 
 */
package ord.mgm.sys.exception;

/**
 * @author priya
 *
 */
public class ItemNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ItemNotFoundException(final String errorMsg) {
		super(errorMsg);
	}

	@Override
	public String toString() {
		return ("ItemNotFoundException occured: " + this.getMessage());
	}
	
}
