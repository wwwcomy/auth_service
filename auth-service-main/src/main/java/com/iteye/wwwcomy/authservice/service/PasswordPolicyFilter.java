package com.iteye.wwwcomy.authservice.service;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;

public interface PasswordPolicyFilter {
    PasswordValidationResult isValid(String password, boolean throwExceptions);

    default PasswordValidationResult isValid(String password) {
        return isValid(password, true);
    }
}
