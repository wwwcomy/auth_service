package com.iteye.wwwcomy.authservice.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.exception.EntityNotFoundException;

public class EntityNotFoundExceptionTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        EntityNotFoundException exception = new EntityNotFoundException();
        exception = new EntityNotFoundException("msg");
        Assert.assertEquals("msg", exception.getMessage());
    }

}
