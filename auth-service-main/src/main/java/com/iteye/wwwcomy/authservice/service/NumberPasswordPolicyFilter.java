package com.iteye.wwwcomy.authservice.service;

import org.springframework.stereotype.Component;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;
import com.iteye.wwwcomy.authservice.util.PasswordUtil;

@Component
public class NumberPasswordPolicyFilter implements PasswordPolicyFilter {

    public static final String ERROR_MSG = "Password must have numbers";

    @Override
    public PasswordValidationResult isValid(String password, boolean throwExceptions) {
        boolean isValid = PasswordUtil.hasNumber(password);
        if (isValid) {
            return new PasswordValidationResult(true);
        } else {
            if (throwExceptions) {
                throw new InvalidPasswordFormatException(ERROR_MSG);
            } else {
                return new PasswordValidationResult(false, ERROR_MSG);
            }
        }
    }

}
