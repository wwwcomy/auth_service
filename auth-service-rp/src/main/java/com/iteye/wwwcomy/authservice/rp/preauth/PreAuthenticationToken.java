package com.iteye.wwwcomy.authservice.rp.preauth;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class PreAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1375745822100195599L;

    public PreAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public PreAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

}
