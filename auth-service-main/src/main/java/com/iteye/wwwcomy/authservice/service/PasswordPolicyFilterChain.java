package com.iteye.wwwcomy.authservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;

public class PasswordPolicyFilterChain implements PasswordPolicyFilter {

    private List<PasswordPolicyFilter> filters;

    public List<PasswordPolicyFilter> getFilters() {
        if (this.filters == null) {
            this.filters = new ArrayList<>();
        }
        return this.filters;
    }

    @Override
    public PasswordValidationResult isValid(String password, boolean throwExceptions) {
        if (throwExceptions) {
            for (PasswordPolicyFilter filter : filters) {
                // we don't need to know the result, as there'll be exceptions if the password
                // is invalid
                filter.isValid(password, throwExceptions);
            }
            return new PasswordValidationResult(true);
        } else {
            PasswordValidationResult finalResult = new PasswordValidationResult(true);
            for (PasswordPolicyFilter filter : filters) {
                PasswordValidationResult result = filter.isValid(password, throwExceptions);
                if (result.isValid()) {
                    continue;
                } else {
                    finalResult.setValid(false);
                    if (!StringUtils.hasLength(finalResult.getInvalidReason())) {
                        finalResult.setInvalidReason(result.getInvalidReason());
                    } else {
                        finalResult.setInvalidReason(finalResult.getInvalidReason() + "," + result.getInvalidReason());
                    }
                }
            }
            return finalResult;
        }
    }

}
