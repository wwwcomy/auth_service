package com.iteye.wwwcomy.authservice.rp.preauth;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthFilter;
import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthenticationManager;
import com.iteye.wwwcomy.authservice.rp.preauth.PreAuthenticationToken;
import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

@RunWith(SpringRunner.class)
public class PreAuthFilterTest {
    private static final String TOKEN_KEY = "token";
    private static final String TOKEN_STRING = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTUwODgxNDMzNCwiZXhwIjoxNTQwMzUwMzM0LCJpc3MiOiJBVVRIX1NFUlZJQ0UiLCJST0xFIjpbIlNVUEVSX0FETUlOIiwiQURNSU4iLCJVU0VSIl19.V9zaXv-yzwrIGzZnR6NPyu0WRUOPmpt4n7jT_XtINQwHx6UI_gOnIxTpWX2r3vVPAbcQ9Fp9kio72CXMxRZbiw";

    @Configuration
    @Import(value = { PreAuthenticationManager.class, TokenValidationService.class })
    static class Config {
        @Autowired
        private PreAuthenticationManager authenticationManager;

        @Bean
        PreAuthFilter preAuthFilter() throws Exception {
            PreAuthFilter preAuthFilter = new PreAuthFilter();
            preAuthFilter.setAuthenticationManager(authenticationManager);
            return preAuthFilter;
        }
    }

    @MockBean
    private HttpServletRequest request;

    @Before
    public void setUp() {
        Mockito.when(request.getHeader(TOKEN_KEY)).thenReturn(TOKEN_STRING);
    }

    @Autowired
    private PreAuthFilter preAuthFilter;

    @Test
    public void canGetPreAuthenticatedPrincipal() {
        Object o = preAuthFilter.getPreAuthenticatedPrincipal(request);
        Assert.assertTrue(o instanceof PreAuthenticationToken);
        PreAuthenticationToken preAuthToken = (PreAuthenticationToken) o;
        Assert.assertEquals("N/A", preAuthToken.getCredentials());
    }

    @Test
    public void canGetNullWithoutToken() {
        Mockito.when(request.getHeader(TOKEN_KEY)).thenReturn("");
        Assert.assertNull(preAuthFilter.getPreAuthenticatedPrincipal(request));
    }

    @Test
    public void canGetPreAuthenticatedCredentials() {
        Assert.assertEquals("N/A", preAuthFilter.getPreAuthenticatedCredentials(request));
    }
}
