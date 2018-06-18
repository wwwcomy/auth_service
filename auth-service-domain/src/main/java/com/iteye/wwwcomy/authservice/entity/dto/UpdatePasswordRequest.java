package com.iteye.wwwcomy.authservice.entity.dto;

public class UpdatePasswordRequest {
    private String orginalPassword;
    private String newPassword;
    private String repeatPassword;

    public String getOrginalPassword() {
        return orginalPassword;
    }

    public void setOrginalPassword(String orginalPassword) {
        this.orginalPassword = orginalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
