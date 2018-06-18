package com.iteye.wwwcomy.authservice.rp.preauth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthenticationToken;

public class PreAuthenticationTokenTest {
    @Before
    public void setUp() {
    }

    @Test
    public void canGetJwtToken() {
        String tokenString = "token";
        PreAuthenticationToken token = new PreAuthenticationToken(tokenString, tokenString);
        token = new PreAuthenticationToken(tokenString, tokenString, null);
        token.setJwtToken(tokenString);
        Assert.assertEquals(tokenString, token.getJwtToken());
    }
}
