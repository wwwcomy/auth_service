package com.iteye.wwwcomy.authservice.exception;

/**
 * Indicates that the input parameter is invalid
 *
 */
public class InvalidParameterException extends RuntimeException {
    private static final long serialVersionUID = 2307749167803827788L;

    public InvalidParameterException() {
    }

    public InvalidParameterException(String s) {
        super(s);
    }
}
