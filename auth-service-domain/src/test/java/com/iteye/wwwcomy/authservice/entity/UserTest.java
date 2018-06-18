package com.iteye.wwwcomy.authservice.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.iteye.wwwcomy.authservice.entity.Role;
import com.iteye.wwwcomy.authservice.entity.User;

public class UserTest {
    private static final String EXISTING_ROLE = "existingRole";
    private static final String NEW_ROLE = "newRole";
    private static final String NON_EXISTING_ROLE = "nonExistingRole";
    private Set<Role> roleSet;

    @Before
    public void setUp() {
        roleSet = new HashSet<>();
        roleSet.add(new Role(EXISTING_ROLE));
    }

    @Test
    public void canGetSetProperties() {
        String uniqueId = UUID.randomUUID().toString();
        User user = new User();
        user = new User("initUser", null);
        user = new User("initUser");
        user.setId(uniqueId);
        user.setEnabled(true);
        user.setHashedPassword("hashed");
        user.setName("newName");
        user.setPassword("password");
        user.setRoles(roleSet);
        Assert.assertEquals(uniqueId, user.getId());
        Assert.assertTrue(user.isEnabled());
        Assert.assertEquals("hashed", user.getHashedPassword());
        Assert.assertEquals("newName", user.getName());
        Assert.assertEquals("password", user.getPassword());
        Assert.assertEquals(roleSet, user.getRoles());
        Assert.assertNotNull(user.getSalt());
    }

    @Test
    public void canMatches() {
        User user = new User();
        user = new User("initUser", "password");
        Assert.assertTrue(user.matches("password"));
    }

    @Test
    public void canEnsurePassword() {
        User user = new User();
        user.setPassword("password");
        user.ensureGeneratingHashedPassword();
        user.toString();
        Assert.assertTrue(user.matches("password"));
    }

    @Test
    public void canGetRoles() {
        User user = new User();
        Assert.assertTrue(user.getRoles().isEmpty());
    }

    @Test
    public void canGetRoleNames1() {
        User user = new User();
        Assert.assertTrue(user.getRoleNames().isEmpty());
    }

    @Test
    public void canGetRoleNames2() {
        User user = new User();
        user.setRoles(roleSet);
        Assert.assertEquals(1, user.getRoleNames().size());
        Assert.assertEquals(EXISTING_ROLE, user.getRoleNames().iterator().next());
    }

    @Test
    public void canAddRole() {
        User user = new User("initUser");
        user.setRoles(roleSet);
        Assert.assertTrue(user.addRole(new Role(NEW_ROLE)));
        Assert.assertEquals(2, user.getRoles().size());
    }

    @Test
    public void cannotAddExistingUser() {
        User user = new User("initUser");
        user.setRoles(roleSet);
        Assert.assertFalse(user.addRole(new Role(EXISTING_ROLE)));
        Assert.assertEquals(1, user.getRoles().size());
    }

    @Test
    public void cannotDelNonExistUser() {
        User user = new User("initUser");
        user.setRoles(roleSet);
        Assert.assertFalse(user.deleteRole(new Role(NON_EXISTING_ROLE)));
        Assert.assertEquals(1, user.getRoles().size());
    }

    @Test
    public void canDelExistUser() {
        User user = new User("initUser");
        user.setRoles(roleSet);
        Assert.assertTrue(user.deleteRole(new Role(EXISTING_ROLE)));
        Assert.assertEquals(0, user.getRoles().size());
    }

}
