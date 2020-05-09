package com.laattre.backen.web.error;

public class DisabledException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6811419262810429164L;

	public DisabledException() {
        super();
    }

    public DisabledException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DisabledException(final String message) {
        super(message);
    }

    public DisabledException(final Throwable cause) {
        super(cause);
    }

}
