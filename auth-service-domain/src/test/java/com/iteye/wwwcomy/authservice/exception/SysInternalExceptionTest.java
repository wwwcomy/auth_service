package com.iteye.wwwcomy.authservice.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.exception.SysInternalException;

public class SysInternalExceptionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        SysInternalException exception = new SysInternalException();
        exception = new SysInternalException("msg");
        Assert.assertEquals("msg", exception.getMessage());
    }

}
