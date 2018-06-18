package com.iteye.wwwcomy.authservice.util;

import org.junit.Assert;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.util.PasswordBuilder;

public class PasswordBuilderTest {
    @Test
    public void canBuildPassword() {
        PasswordBuilder builder = PasswordBuilder.getDefaultBuilder();
        builder.setSize(32);
        Assert.assertNotNull(builder.build());
        Assert.assertEquals(32, builder.build().length());
    }
}
