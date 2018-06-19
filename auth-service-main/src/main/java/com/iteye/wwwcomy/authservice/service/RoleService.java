package com.iteye.wwwcomy.authservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.exception.EntityAlreadyExistsException;
import com.iteye.wwwcomy.authservice.exception.EntityNotFoundException;
import com.iteye.wwwcomy.authservice.repository.RoleRepository;

@Service
public class RoleService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserService userService;

	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

	/**
	 * Finds the role entity using a input role name. This method will throw
	 * {@link EntityNotFoundException} if the role is not found.
	 * 
	 * @param roleName
	 *            the input role name
	 * @return the Role found
	 */
	public Role findRole(String roleName) {
		Role role = roleRepository.findByName(roleName);
		if (role == null) {
			throw new EntityNotFoundException("Role not found");
		}
		return role;
	}

	public Role createRole(Role role) {
		if (null != roleRepository.findByName(role.getName())) {
			throw new EntityAlreadyExistsException("Role already exists");
		}
		return roleRepository.save(role);
	}

	public Role addRoleToUsers(String roleName, String userNames) {
		logger.info("Adding users the role:" + roleName);
		Role role = findRole(roleName);
		List<String> userNameList = Arrays.asList(userNames.split(","));
		userNameList.stream().forEach(userName -> {
			User user = userService.findUser(userName);
			user.addRole(role);
			role.addUser(user);
		});
		return roleRepository.save(role);
	}

	public Role deleteRoleFromUsers(String roleName, String userNames) {
		logger.info("Deleting role from users:" + roleName);
		Role role = findRole(roleName);
		List<String> userNameList = Arrays.asList(userNames.split(","));
		userNameList.stream().forEach(userName -> {
			User user = userService.findUser(userName);
			user.deleteRole(role);
			role.deleteUser(user);
		});
		return roleRepository.save(role);
	}

	public Object save(Role role) {
		return roleRepository.save(role);
	}

	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

	public Role deleteRole(String roleName) {
		Role role = findRole(roleName);
		Set<User> users = role.getUsers();
		for (User user : users) {
			user.getRoles().remove(role);
		}
		roleRepository.delete(role);
		return role;
	}
}
