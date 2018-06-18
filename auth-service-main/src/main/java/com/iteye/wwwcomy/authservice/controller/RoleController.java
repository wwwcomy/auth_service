package com.iteye.wwwcomy.authservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.dto.UserReference;
import com.iteye.wwwcomy.authservice.service.RoleService;

@RestController
@RequestMapping(value = "/roles")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Role> getRoles() {
        return roleService.listRoles();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{roleName}")
    public Role getRole(@PathVariable String roleName) {
        return roleService.findRole(roleName);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
    public Role createRole(@RequestBody Role role, HttpServletResponse httpResponse) {
        Role createdRole = roleService.createRole(role);
        httpResponse.setStatus(HttpStatus.CREATED.value());
        return createdRole;
    }

    @RequestMapping(value = "/{ROLE_PARAM}/users", method = RequestMethod.POST, consumes = { "application/json" })
    public Role addRoleToUser(@PathVariable("ROLE_PARAM") String roleName, @RequestBody UserReference userReference,
            HttpServletResponse httpResponse) {
        String userRefrence = userReference.getUserReference();
        return roleService.addRoleToUsers(roleName, userRefrence);
    }

    @RequestMapping(value = "/{ROLE_PARAM}/users", method = RequestMethod.DELETE)
    public void deleteRoleFromUser(@PathVariable("ROLE_PARAM") String roleName, UserReference userReference,
            HttpServletResponse httpResponse) {
        String userRefrence = userReference.getUserReference();
        roleService.deleteRoleFromUsers(roleName, userRefrence);
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "/{roleName}")
    public void deleteUser(@PathVariable String roleName, HttpServletResponse httpResponse) {
        roleService.deleteRole(roleName);
        httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
