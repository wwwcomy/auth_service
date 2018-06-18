package com.iteye.wwwcomy.authservice.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.exception.ValidationErrorMessage;

/**
 * Unit test for {@link ValidationErrorMessage}
 */
public class ValidationErrorMessageTest {
    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        ValidationErrorMessage msg = new ValidationErrorMessage();
        msg.setCode("CODE");
        msg.setDetail("DETAIL");
        msg.setField("FIELD");
        Assert.assertEquals("CODE", msg.getCode());
        Assert.assertEquals("DETAIL", msg.getDetail());
        Assert.assertEquals("FIELD", msg.getField());
    }

    @Test
    public void canGetSetProperties1() {
        ValidationErrorMessage msg = new ValidationErrorMessage("CODE", "DETAIL");
        Assert.assertEquals("CODE", msg.getCode());
        Assert.assertEquals("DETAIL", msg.getDetail());
        Assert.assertEquals(null, msg.getField());
    }

    @Test
    public void canGetSetProperties2() {
        ValidationErrorMessage msg = new ValidationErrorMessage("CODE", "DETAIL", "FIELD");
        Assert.assertEquals("CODE", msg.getCode());
        Assert.assertEquals("DETAIL", msg.getDetail());
        Assert.assertEquals("FIELD", msg.getField());
    }
}
