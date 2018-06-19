package com.iteye.wwwcomy.authservice.rp.preauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

@Component
public class PreAuthenticationManager implements AuthenticationManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TokenValidationService tokenService;

	/**
	 * An <code>AuthenticationManager</code> must honour the following contract
	 * concerning exceptions:
	 * <ul>
	 * <li>A {@link DisabledException} must be thrown if an account is disabled and
	 * the <code>AuthenticationManager</code> can test for this state.</li>
	 * <li>A {@link LockedException} must be thrown if an account is locked and the
	 * <code>AuthenticationManager</code> can test for account locking.</li>
	 * <li>A {@link BadCredentialsException} must be thrown if incorrect credentials
	 * are presented. Whilst the above exceptions are optional, an
	 * <code>AuthenticationManager</code> must <B>always</B> test credentials.</li>
	 * </ul>
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!(authentication instanceof PreAuthenticatedAuthenticationToken)) {
			logger.warn("PreAuthenticationManager processing an unsupported AuthenticationToken");
			return null;
		}
		PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = (PreAuthenticatedAuthenticationToken) authentication;
		PreAuthenticationToken preAuthToken = (PreAuthenticationToken) preAuthenticatedAuthenticationToken
				.getPrincipal();
		String jwtToken = preAuthToken.getJwtToken();
		if (!tokenService.validate(jwtToken)) {
			throw new BadCredentialsException("The jwtToken in the PreAuthenticationToken is not valid!");
		}
		return preAuthToken;
	}

}
