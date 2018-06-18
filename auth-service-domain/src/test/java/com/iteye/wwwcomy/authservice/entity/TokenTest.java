package com.iteye.wwwcomy.authservice.entity;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.Token;

public class TokenTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        String uniqueId = UUID.randomUUID().toString();
        Token token = new Token();
        token = new Token("init");
        token.setContent("a.b.c");
        token.setId(uniqueId);
        token.setMd5Content("md5");
        token.toString();
        Assert.assertEquals(uniqueId, token.getId());
        Assert.assertEquals("md5", token.getMd5Content());
        Assert.assertEquals("a.b.c", token.getContent());
    }

}
