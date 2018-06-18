package com.iteye.wwwcomy.authservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;

@Component
public class LengthPasswordPolicyFilter implements PasswordPolicyFilter {

    @Value("${password.lengthRequired:6}")
    private int lengthRequired;

    public static final String ERROR_MSG = "Length of password must be more than ";

    public void setLengthRequired(int lengthRequired) {
        this.lengthRequired = lengthRequired;
    }

    public String getErrorMsg() {
        return ERROR_MSG + lengthRequired;
    }

    @Override
    public PasswordValidationResult isValid(String password, boolean throwExceptions) {
        boolean isValid = password.length() >= lengthRequired;
        if (isValid) {
            return new PasswordValidationResult(true);
        } else {
            if (throwExceptions) {
                throw new InvalidPasswordFormatException(getErrorMsg());
            } else {
                return new PasswordValidationResult(false, getErrorMsg());
            }
        }
    }

}
