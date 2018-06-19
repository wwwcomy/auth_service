package com.iteye.wwwcomy.authservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.User;
import com.iteye.wwwcomy.authservice.exception.AuthenticationException;
import com.iteye.wwwcomy.authservice.exception.EntityAlreadyExistsException;
import com.iteye.wwwcomy.authservice.exception.EntityNotFoundException;
import com.iteye.wwwcomy.authservice.repository.UserRepository;

@Service
public class UserService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleService roleService;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	/**
	 * Finds the user entity using a input user name. This method will throw
	 * {@link EntityNotFoundException} if the user is not found.
	 * 
	 * @param userName
	 *            the input user name
	 * @return the user found
	 */
	public User findUser(String userName) {
		User user = userRepository.findByMail(userName);
		if (user == null) {
			throw new EntityNotFoundException("User not found");
		}
		return user;
	}

	/**
	 * Finds the user entity using a input user name. This method will return null
	 * if the user is not found.
	 * 
	 * @param userName
	 *            the input user name
	 * @return the user found or NULL
	 */
	public User findByName(String userName) {
		return userRepository.findByMail(userName);
	}

	public User findByMail(String mail) {
		return userRepository.findByMail(mail);
	}

	public User createUser(User user) {
		if (null != findByName(user.getName())) {
			throw new EntityAlreadyExistsException("User already exists");
		}
		if (null != findByMail(user.getMail())) {
			throw new EntityAlreadyExistsException("User with the same email already exists");
		}
		user.ensureGeneratingHashedPassword();
		return userRepository.save(user);
	}

	@Transactional
	public User addRoleToUser(String userName, String roleNames) {
		logger.info("Adding roles to user:" + userName);
		User user = findUser(userName);
		List<String> roleNameList = Arrays.asList(roleNames.split(","));
		roleNameList.stream().forEach(roleName -> {
			Role role = roleService.findRole(roleName);
			user.addRole(role);
			role.addUser(user);
		});
		return userRepository.save(user);
	}

	public User deleteRoleFromUser(String userName, String roles) {
		logger.info("Deleting roles from user:" + userName);
		User user = findUser(userName);
		List<String> roleNameList = Arrays.asList(roles.split(","));
		roleNameList.stream().forEach(roleName -> {
			Role role = roleService.findRole(roleName);
			user.deleteRole(role);
			role.deleteUser(user);
		});
		return userRepository.save(user);
	}

	public Object save(User user) {
		return userRepository.save(user);
	}

	public User deleteUser(String userName) {
		User user = findUser(userName);
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			role.getUsers().remove(user);
		}
		userRepository.delete(user);
		return user;
	}

	public void changePassword(String userName, String orginalPassword, String newPassword) {
		User existingUser = findUser(userName);
		if (!existingUser.matches(orginalPassword)) {
			logger.error("User " + userName + " failed to login.");
			throw new AuthenticationException("User failed to authenticate");
		} else {
			existingUser.resetHashedPassword(newPassword);
			userRepository.save(existingUser);
		}
	}

}
