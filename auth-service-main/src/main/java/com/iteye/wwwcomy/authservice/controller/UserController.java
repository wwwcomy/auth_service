package com.iteye.wwwcomy.authservice.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.authservice.entity.PasswordValidationResult;
import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.entity.dto.RoleReference;
import com.iteye.wwwcomy.authservice.exception.InvalidParameterException;
import com.iteye.wwwcomy.authservice.exception.InvalidPasswordFormatException;
import com.iteye.wwwcomy.authservice.service.PasswordPolicyFilterChain;
import com.iteye.wwwcomy.authservice.service.UserService;

@RestController
@RequestMapping(value = "/users")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordPolicyFilterChain passwordFilterChain;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<User> listUsers() {
        return userService.getUsers();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{userName}")
    public User getUser(@PathVariable String userName) {
        return userService.findUser(userName);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
    public User createUser(@RequestBody @Validated User user, BindingResult bindingResult,
            HttpServletResponse httpResponse,
            @RequestParam(value = "getAllPasswordError", required = false, defaultValue = "") String getAllPasswordError) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
            }
            throw new InvalidParameterException(sb.toString());
        }
        PasswordValidationResult result = passwordFilterChain.isValid(user.getPassword(),
                !"true".equalsIgnoreCase(getAllPasswordError));
        if (!result.isValid()) {
            throw new InvalidPasswordFormatException(result.getInvalidReason());
        }
        User createdUser = userService.createUser(user);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        return createdUser;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{userName}")
    public void deleteUser(@PathVariable String userName, HttpServletResponse httpResponse) {
        userService.deleteUser(userName);
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }

    @RequestMapping(value = "/{USER_PARAM}/roles", method = RequestMethod.POST, consumes = { "application/json" })
    public User addRoleToUser(@PathVariable("USER_PARAM") String userName, @RequestBody RoleReference roleReference,
            HttpServletResponse httpResponse) {
        String roleRefrence = roleReference.getRoleReference();
        return userService.addRoleToUser(userName, roleRefrence);
    }

    @RequestMapping(value = "/{USER_PARAM}/roles", method = RequestMethod.GET)
    public Set<Role> getRoles(@PathVariable("USER_PARAM") String userName, HttpServletResponse httpResponse) {
        User user = userService.findUser(userName);
        return user.getRoles();
    }

    /**
     * Removes the role of the given user.
     * 
     * @param userId
     *            name or id of the user
     * @param roleId
     *            name or id of the role
     */
    @RequestMapping(value = "/{USER_PARAM}/roles", method = RequestMethod.DELETE)
    public void deleteRoleFromUser(@PathVariable("USER_PARAM") String userId, @RequestBody RoleReference roleReference,
            HttpServletResponse httpResponse) {
        String roleRefrence = roleReference.getRoleReference();
        userService.deleteRoleFromUser(userId, roleRefrence);
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
