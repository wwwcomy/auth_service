package com.iteye.wwwcomy.authservice.exception;

/**
 * Indicates that the entity is not found
 *
 */
public class EntityNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -3350262420867359131L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String s) {
		super(s);
	}
}
