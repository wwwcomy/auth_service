package com.iteye.wwwcomy.authservice.exception;

public class InvalidPasswordFormatException extends RuntimeException {

    private static final long serialVersionUID = 6064421310138538825L;

    public InvalidPasswordFormatException() {
    }

    public InvalidPasswordFormatException(String s) {
        super(s);
    }
}