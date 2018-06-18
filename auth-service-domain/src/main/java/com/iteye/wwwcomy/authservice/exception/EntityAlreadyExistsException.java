package com.iteye.wwwcomy.authservice.exception;

/**
 * Indicates that the entity already exists
 *
 */
public class EntityAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = -3350262420867359131L;

	public EntityAlreadyExistsException() {
	}

	public EntityAlreadyExistsException(String s) {
		super(s);
	}
}
