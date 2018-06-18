package com.iteye.wwwcomy.authservice.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.exception.EntityAlreadyExistsException;

public class EntityAlreadyExistsExceptionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        EntityAlreadyExistsException exception = new EntityAlreadyExistsException();
        exception = new EntityAlreadyExistsException("msg");
        Assert.assertEquals("msg", exception.getMessage());
    }

}
