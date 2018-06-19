package com.iteye.wwwcomy.authservice.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.entity.dto.TokenWrapper;
import com.iteye.wwwcomy.authservice.exception.AuthenticationException;
import com.iteye.wwwcomy.authservice.repository.UserRepository;
import com.iteye.wwwcomy.authservice.service.TokenService;

@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticationController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${authservice.token.cookie.key:token}")
	private String tokenCookieKey;

	@Value("${authservice.token.cookie.secure:false}")
	private boolean cookieSecure;
	@Value("${authservice.token.cookie.httponly:false}")
	private boolean cookieHttpOnly;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(method = RequestMethod.POST)
	public TokenWrapper Authenticate(@RequestBody User user, HttpServletResponse response) {
		logger.info("Authenticating User");
		String userName = user.getName();
		User existingUser = userRepository.findByMail(userName);
		if (null == existingUser) {
			logger.error("User not exist");
			throw new AuthenticationException("User failed to authenticate");
		}
		if (!existingUser.matches(user.getPassword())) {
			logger.error("User failed to login.");
			throw new AuthenticationException("User failed to authenticate");
		}
		String token = tokenService.createAndStoreToken(userName);
		Cookie tokenCookie = new Cookie(tokenCookieKey, token);
		tokenCookie.setHttpOnly(cookieHttpOnly);
		tokenCookie.setSecure(cookieSecure);
		response.addCookie(tokenCookie);
		return new TokenWrapper(token);
	}

	public String getTokenCookieKey() {
		return tokenCookieKey;
	}

	public void setTokenCookieKey(String tokenCookieKey) {
		this.tokenCookieKey = tokenCookieKey;
	}

}
