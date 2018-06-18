package com.iteye.wwwcomy.authservice.util;

import org.junit.Assert;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.util.SaltUtil;

public class SaltUtilTest {
    @Test
    public void canGenerateSalt() {
        Assert.assertNotNull(SaltUtil.generateSaltString());
    }
}
