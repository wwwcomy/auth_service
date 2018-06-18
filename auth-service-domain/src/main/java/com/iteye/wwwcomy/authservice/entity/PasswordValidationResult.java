package com.iteye.wwwcomy.authservice.entity;

public class PasswordValidationResult {
    private boolean valid;
    private String invalidReason;

    public PasswordValidationResult() {
        this.valid = false;
        this.invalidReason = "";
    }

    public PasswordValidationResult(boolean valid) {
        this.valid = valid;
        this.invalidReason = "";
    }

    public PasswordValidationResult(boolean valid, String invalidReason) {
        this.valid = valid;
        this.invalidReason = invalidReason;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }
}
