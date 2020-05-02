package com.laattre.backen.web.error;

public class CustomBadCredentialsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5312259412212482765L;

	 public CustomBadCredentialsException() {
	        super();
	    }

	    public CustomBadCredentialsException(final String message, final Throwable cause) {
	        super(message, cause);
	    }

	    public CustomBadCredentialsException(final String message) {
	        super(message);
	    }

	    public CustomBadCredentialsException(final Throwable cause) {
	        super(cause);
	    }
}
