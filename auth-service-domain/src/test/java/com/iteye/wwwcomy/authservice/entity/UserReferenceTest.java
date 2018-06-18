package com.iteye.wwwcomy.authservice.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.dto.UserReference;

public class UserReferenceTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        UserReference userReference = new UserReference();
        userReference.setUserReference("ADMIN");
        Assert.assertEquals("ADMIN", userReference.getUserReference());
    }

}
