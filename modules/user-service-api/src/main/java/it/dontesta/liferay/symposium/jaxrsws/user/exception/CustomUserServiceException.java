/**
 * 
 */
package it.dontesta.liferay.symposium.jaxrsws.user.exception;

/**
 * @author amusarra
 *
 */
public class CustomUserServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8405226850508960240L;

	public CustomUserServiceException() {
		super();
	}

	public CustomUserServiceException(String message) {
		super(message);
	}

	public CustomUserServiceException(Throwable cause) {
		super(cause);
	}
}
