package com.iteye.wwwcomy.authservice.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;
import com.iteye.wwwcomy.authservice.service.CharPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.LengthPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.NumberPasswordPolicyFilter;
import com.iteye.wwwcomy.authservice.service.PasswordPolicyFilterChain;

public class PasswordPolicyFilterChainTest {
    private PasswordPolicyFilterChain filterChain;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init() {
        filterChain = new PasswordPolicyFilterChain();
        LengthPasswordPolicyFilter lengthFilter = new LengthPasswordPolicyFilter();
        lengthFilter.setLengthRequired(6);
        filterChain.getFilters().add(lengthFilter);
        filterChain.getFilters().add(new NumberPasswordPolicyFilter());
        filterChain.getFilters().add(new CharPasswordPolicyFilter());
    }

    @Test
    public void canFailValidateLength() {
        this.expectedException.expect(InvalidPasswordFormatException.class);
        this.expectedException.expectMessage(LengthPasswordPolicyFilter.ERROR_MSG);
        filterChain.isValid("a");
    }

    @Test
    public void canFailValidateNumber() {
        this.expectedException.expect(InvalidPasswordFormatException.class);
        this.expectedException.expectMessage(NumberPasswordPolicyFilter.ERROR_MSG);
        filterChain.isValid("abcdef");
    }

    @Test
    public void canFailValidateChar() {
        this.expectedException.expect(InvalidPasswordFormatException.class);
        this.expectedException.expectMessage(CharPasswordPolicyFilter.ERROR_MSG);
        filterChain.isValid("123456");
    }

    @Test
    public void canValidate() {
        PasswordValidationResult result = filterChain.isValid("a1s2d3");
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void canFailValidateForAll() {
        PasswordValidationResult result = filterChain.isValid("---", false);
        Assert.assertFalse(result.isValid());
        Assert.assertTrue(result.getInvalidReason().split(",").length == 3);
        Assert.assertTrue(result.getInvalidReason().contains(CharPasswordPolicyFilter.ERROR_MSG));
        Assert.assertTrue(result.getInvalidReason().contains(NumberPasswordPolicyFilter.ERROR_MSG));
        Assert.assertTrue(result.getInvalidReason().contains(LengthPasswordPolicyFilter.ERROR_MSG));
    }
}
