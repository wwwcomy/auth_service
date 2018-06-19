package com.iteye.wwwcomy.authservice.controller.advice;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.iteye.wwwcomy.authservice.exception.AuthenticationException;
import com.iteye.wwwcomy.authservice.exception.EntityAlreadyExistsException;
import com.iteye.wwwcomy.authservice.exception.EntityNotFoundException;
import com.iteye.wwwcomy.authservice.exception.InvalidParameterException;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;
import com.iteye.wwwcomy.authservice.exception.ValidationErrorMessage;

@ControllerAdvice
public class ErrorHandlingAdvice {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ValidationErrorMessage handle(Exception ex) throws Exception {
		logger.error("Entering exception handler for Exception");
		logger.error("Handling " + ex.getClass().toString() + ": " + ex.getMessage(), ex);
		throw ex;
		// return new ValidationErrorMessage("exception", "An error occurred");
	}

	/**
	 * Catches any {@link EntityAlreadyExistsException} and returns a response with
	 * bad request status.
	 *
	 */
	@ExceptionHandler({ EntityAlreadyExistsException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorMessage handle(EntityAlreadyExistsException exception, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return new ValidationErrorMessage("exception", exception.getMessage());
	}

	/**
	 * Catches any {@link EntityNotFoundException} and returns a response with not
	 * found status.
	 *
	 */
	@ExceptionHandler({ EntityNotFoundException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ValidationErrorMessage handle(EntityNotFoundException exception, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return new ValidationErrorMessage("exception", exception.getMessage());
	}

	/**
	 * Catches any {@link EntityNotFoundException} and returns a response with 401
	 * status.
	 *
	 */
	@ExceptionHandler({ AuthenticationException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ValidationErrorMessage handle(AuthenticationException exception, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return new ValidationErrorMessage("exception", exception.getMessage());
	}

	/**
	 * Catches any {@link EntityNotFoundException} and returns a response with 401
	 * status.
	 *
	 */
	@ExceptionHandler({ InvalidParameterException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorMessage handle(InvalidParameterException exception, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return new ValidationErrorMessage("exception", exception.getMessage());
	}

	@ExceptionHandler({ InvalidPasswordFormatException.class })
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorMessage handle(InvalidPasswordFormatException exception, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return new ValidationErrorMessage("exception", exception.getMessage());
	}
}
