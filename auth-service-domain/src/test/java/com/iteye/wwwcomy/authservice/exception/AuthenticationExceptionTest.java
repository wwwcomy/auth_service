package com.iteye.wwwcomy.authservice.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.exception.AuthenticationException;

public class AuthenticationExceptionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        AuthenticationException exception = new AuthenticationException();
        exception = new AuthenticationException("msg");
        Assert.assertEquals("msg", exception.getMessage());
    }

}
