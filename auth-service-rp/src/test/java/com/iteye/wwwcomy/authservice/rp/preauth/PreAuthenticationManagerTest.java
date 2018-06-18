package com.iteye.wwwcomy.authservice.rp.preauth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;

import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthenticationManager;
import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthenticationToken;
import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

@RunWith(SpringRunner.class)
public class PreAuthenticationManagerTest {
    @Autowired
    private PreAuthenticationManager authenticationManager;
    @MockBean
    private TokenValidationService tokenValidationService;

    @Configuration
    @Import(value = { PreAuthenticationManager.class })
    static class Config {
    }

    @Before
    public void setUp() {
        Mockito.when(tokenValidationService.validate(Mockito.anyString())).thenReturn(true);
    }

    @Test
    public void canAuthenticate() {
        String tokenString = "token";
        PreAuthenticationToken token = new PreAuthenticationToken(tokenString, tokenString);
        token = new PreAuthenticationToken(tokenString, tokenString, null);
        token.setJwtToken(tokenString);
        PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken(token, token);
        Assert.assertEquals(token, authenticationManager.authenticate(preAuthToken));
    }

    @Test
    public void canReturnNullForOtherAuthenticate() {
        Assert.assertNull(authenticationManager.authenticate(new PreAuthenticationToken(null, null)));
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void canThrowExceptionOnFailing() {
        expectedException.expect(BadCredentialsException.class);
        expectedException.expectMessage("The jwtToken in the PreAuthenticationToken is not valid!");
        Mockito.when(tokenValidationService.validate(Mockito.anyString())).thenReturn(false);
        String tokenString = "token";
        PreAuthenticationToken token = new PreAuthenticationToken(tokenString, tokenString);
        token = new PreAuthenticationToken(tokenString, tokenString, null);
        token.setJwtToken(tokenString);
        PreAuthenticatedAuthenticationToken preAuthToken = new PreAuthenticatedAuthenticationToken(token, token);
        authenticationManager.authenticate(preAuthToken);
    }
}
