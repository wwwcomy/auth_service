package com.iteye.wwwcomy.authservice.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.dto.RoleReference;

public class RoleReferenceTest {

    @Before
    public void setUp() {
    }

    @Test
    public void canGetSetProperties() {
        RoleReference roleReference = new RoleReference();
        roleReference.setRoleReference("ADMIN");
        Assert.assertEquals("ADMIN", roleReference.getRoleReference());
    }

}
