package com.iteye.wwwcomy.authservice.exception;

/**
 * Indicates that the principal in the request is not authenticated
 *
 */
public class AuthenticationException extends RuntimeException {
	private static final long serialVersionUID = -3350262420867359131L;

	public AuthenticationException() {
	}

	public AuthenticationException(String s) {
		super(s);
	}
}
