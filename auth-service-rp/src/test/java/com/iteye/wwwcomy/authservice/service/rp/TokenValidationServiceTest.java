package com.iteye.wwwcomy.authservice.service.rp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.service.rp.TokenValidationService;

public class TokenValidationServiceTest {

    private String tokenStr = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTUwODgxNDMzNCwiZXhwIjoxNTQwMzUwMzM0LCJpc3MiOiJBVVRIX1NFUlZJQ0UiLCJST0xFIjpbIlNVUEVSX0FETUlOIiwiQURNSU4iLCJVU0VSIl19.V9zaXv-yzwrIGzZnR6NPyu0WRUOPmpt4n7jT_XtINQwHx6UI_gOnIxTpWX2r3vVPAbcQ9Fp9kio72CXMxRZbiw";

    private TokenValidationService tokenValidationService;

    @Before
    public void setUp() {
        tokenValidationService = new TokenValidationService();
    }

    @Test
    public void canGetSubject() {
        Assert.assertEquals("admin", tokenValidationService.getSubject(tokenStr));
    }

    @Test
    public void canValidate() {
        Assert.assertTrue(tokenValidationService.validate(tokenStr));
    }

    @Test
    public void canFailValidateOnError() {
        Assert.assertFalse(tokenValidationService.validate(tokenStr + "1"));
    }

}
