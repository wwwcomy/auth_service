package com.iteye.wwwcomy.authservice.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.User;

public class RoleTest {
    private static final String EXISTING_USER = "existingUser";
    private static final String NEW_USER = "newUser";
    private static final String NON_EXISTING_USER = "nonExistingUser";
    private Set<User> userSet;

    @Before
    public void setUp() {
        userSet = new HashSet<>();
        userSet.add(new User(EXISTING_USER));
    }

    @Test
    public void canGetSetProperties() {
        String uniqueId = UUID.randomUUID().toString();
        Role role = new Role("initRole");
        role.setId(uniqueId);
        role.setName("testRole");
        role.setUsers(userSet);

        String id = role.getId();
        String roleName = role.getName();
        Set<User> users = role.getUsers();
        Assert.assertEquals(uniqueId, id);
        Assert.assertEquals("testRole", roleName);
        Assert.assertEquals(userSet, users);
    }

    @Test
    public void canContainUser() {
        Role role = new Role("initRole");
        role.setUsers(userSet);
        Assert.assertTrue(role.containsUser(EXISTING_USER));
        Assert.assertFalse(role.containsUser(NON_EXISTING_USER));
    }

    @Test
    public void canAddUser() {
        Role role = new Role("initRole");
        role.setUsers(userSet);
        role.addUser(new User(NEW_USER));
        Assert.assertEquals(2, role.getUsers().size());
    }

    @Test
    public void cannotAddExistingUser() {
        Role role = new Role("initRole");
        role.setUsers(userSet);
        Assert.assertFalse(role.addUser(new User(EXISTING_USER)));
        Assert.assertEquals(1, role.getUsers().size());
    }

    @Test
    public void cannotDelNonExistingUser() {
        Role role = new Role("initRole");
        role.setUsers(userSet);
        Assert.assertFalse(role.deleteUser(new User(NON_EXISTING_USER)));
        Assert.assertEquals(1, role.getUsers().size());
    }

    @Test
    public void canDelExistUser() {
        Role role = new Role();
        role.setUsers(userSet);
        role.deleteUser(new User(EXISTING_USER));
        Assert.assertEquals(0, role.getUsers().size());
    }

    @Test
    public void canToString() {
        Role role = new Role();
        role.setUsers(userSet);
        role.toString();
    }
}
