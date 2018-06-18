package com.iteye.wwwcomy.authservice.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = Role.TABLE_NAME)
@JsonInclude(Include.NON_NULL)
public class Role extends BasePersistedObject{
    /** Role Table Name */
    public static final String TABLE_NAME = "T_ROLE";
    /** Join table name for roles and groups */
    public static final String ROLE_USER_JOIN_TABLE_NAME = "T_ROLE_USER";

    private String name;
    /** The set of users associated with this Role */
    private Set<User> users;

    public Role() {
    }

    public Role(String name) {
        this.setName(name);
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = ROLE_USER_JOIN_TABLE_NAME, joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public boolean containsUser(String userName) {
        return getUsers().stream().anyMatch(user -> user.getName().equalsIgnoreCase(userName));
    }

    public User getUserIfExists(String userName) {
        List<User> mappedUsers = getUsers().stream().filter(user -> user.getName().equalsIgnoreCase(userName))
                .collect(Collectors.toList());
        if (mappedUsers.size() > 0) {
            return mappedUsers.get(0);
        } else {
            return null;
        }
    }

    public boolean addUser(User user) {
        if (this.containsUser(user.getName())) {
            return false;
        }
        return users.add(user);
    }

    public boolean deleteUser(User user) {
        User existingUser = getUserIfExists(user.getName());
        if (existingUser == null) {
            return false;
        }
        return users.remove(existingUser);
    }

    @Override
    public String toString() {
        return "Role [id=" + getId() + ", name=" + name + "]";
    }

}
