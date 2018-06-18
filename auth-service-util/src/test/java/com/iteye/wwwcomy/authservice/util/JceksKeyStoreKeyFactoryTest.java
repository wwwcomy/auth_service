package com.iteye.wwwcomy.authservice.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.iteye.wwwcomy.authservice.util.JceksKeyStoreKeyFactory;

public class JceksKeyStoreKeyFactoryTest {
    @Test
    public void canGetSecretKey() {
        JceksKeyStoreKeyFactory factory = new JceksKeyStoreKeyFactory(new ClassPathResource("secret.jks"),
                "foobar".toCharArray());
        Assert.assertNotNull(factory.getSecretKey("test1", "".toCharArray()));
    }
}
