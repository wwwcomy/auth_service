package com.iteye.wwwcomy.authservice.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;
import com.iteye.wwwcomy.authservice.entity.dto.UpdatePasswordRequest;
import com.iteye.wwwcomy.authservice.exception.AuthenticationException;
import com.iteye.wwwcomy.authservice.exception.InvalidParameterException;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;
import com.iteye.wwwcomy.authservice.service.PasswordPolicyFilterChain;
import com.iteye.wwwcomy.authservice.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class PasswordController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordPolicyFilterChain passwordFilterChain;

    @RequestMapping(path = "/{userName}/password", method = RequestMethod.PUT, consumes = { "application/json" })
    public void changePassword(Principal user, @PathVariable String userName,
            @RequestParam(value = "getAllPasswordError", required = false, defaultValue = "") String getAllPasswordError,
            UpdatePasswordRequest updatePasswordRequest) {

        if (!StringUtils.hasLength(updatePasswordRequest.getOrginalPassword())
                || !StringUtils.hasLength(updatePasswordRequest.getNewPassword())) {
            throw new InvalidParameterException("Password cannot be empty.");
        }
        if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getRepeatPassword())) {
            throw new InvalidParameterException("New password are not the same.");
        }
        if (!user.getName().equals(userName)) {
            throw new AuthenticationException("You cannot change other user's password");
        }

        PasswordValidationResult result = passwordFilterChain.isValid(updatePasswordRequest.getNewPassword(),
                !"true".equalsIgnoreCase(getAllPasswordError));
        if (!result.isValid()) {
            throw new InvalidPasswordFormatException(result.getInvalidReason());
        }
        userService.changePassword(userName, updatePasswordRequest.getOrginalPassword(),
                updatePasswordRequest.getNewPassword());
    }
}
