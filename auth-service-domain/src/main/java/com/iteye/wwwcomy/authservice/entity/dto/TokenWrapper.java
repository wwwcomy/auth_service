package com.iteye.wwwcomy.authservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used for wrapping a token node in the json body.
 */
@JsonInclude(Include.NON_NULL)
public class TokenWrapper {
	/** The wrapped token */
	private String token;

	public TokenWrapper() {
	}

	public TokenWrapper(String token) {
		this.token = token;
	}

	@JsonProperty("token")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
