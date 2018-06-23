package com.iteye.wwwcomy.authservice.exception;

public class MethodNotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 7355933209374085533L;

	public MethodNotImplementedException() {
	}

	public MethodNotImplementedException(String s) {
		super(s);
	}

	public MethodNotImplementedException(String s, Throwable e) {
		super(s, e);
	}
}
