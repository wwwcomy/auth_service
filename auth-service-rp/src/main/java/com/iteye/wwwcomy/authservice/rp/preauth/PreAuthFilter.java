package com.iteye.wwwcomy.authservice.rp.preauth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.util.StringUtils;

import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

import io.jsonwebtoken.Claims;

public class PreAuthFilter extends AbstractPreAuthenticatedProcessingFilter implements InitializingBean {
    /** Logging support */
    private static final Logger logger = LoggerFactory.getLogger(PreAuthFilter.class);
    private static final String TOKEN_KEY = "token";

    @Autowired
    private TokenValidationService tokenService;

    @SuppressWarnings("unchecked")
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        logger.trace("building authentication request.");
        String authTokenText = request.getHeader(TOKEN_KEY);
        if (!StringUtils.hasText(authTokenText)) {
            logger.warn("Incoming HTTP request does not have Token header. Aborting pre auth processing. " + "URL: "
                    + request.getRequestURI());
            return null;
        }
        Claims claims = tokenService.getClaims(authTokenText);
        String subject = claims.getSubject();
        Collection<String> roles = (Collection<String>) claims.get("ROLE");
        User user = new User(subject);
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        PreAuthenticationToken authenticationToken = new PreAuthenticationToken(user,
                getPreAuthenticatedCredentials(request), authorities);
        authenticationToken.setJwtToken(authTokenText);
        return authenticationToken;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }

}
