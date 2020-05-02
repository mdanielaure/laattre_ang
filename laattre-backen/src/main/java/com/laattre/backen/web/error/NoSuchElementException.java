package com.laattre.backen.web.error;

public class NoSuchElementException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public NoSuchElementException() {
        super();
    }

    public NoSuchElementException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchElementException(final String message) {
        super(message);
    }

    public NoSuchElementException(final Throwable cause) {
        super(cause);
    }
}
