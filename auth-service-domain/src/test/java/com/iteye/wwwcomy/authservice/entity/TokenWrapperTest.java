package com.iteye.wwwcomy.authservice.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.dto.TokenWrapper;

public class TokenWrapperTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        TokenWrapper wrapper = new TokenWrapper();
        wrapper = new TokenWrapper("content");
        wrapper.setToken("a.b.c");
        Assert.assertEquals("a.b.c", wrapper.getToken());
    }

}
