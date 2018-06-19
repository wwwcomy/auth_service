package com.iteye.wwwcomy.authservice.entity;

import java.security.MessageDigest;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.iteye.wwwcomy.authservice.util.SaltUtil;

@Entity
@Table(name = User.TABLE_NAME)
@JsonInclude(Include.NON_NULL)
public class User extends BasePersistedObject implements Principal {
	public static final String TABLE_NAME = "T_USER";
	public static final String PASSWORD_COL = "PASSWORD";

	@NotBlank(message = "Cannot be empty")
	@Email(message = "Wrong e-mail format")
	private String mail;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@JsonIgnore
	private String hashedPassword;
	@JsonIgnore
	private String salt;
	private boolean enabled = true;
	/**
	 * Set of roles that belong to the group
	 */
	@JsonProperty(access = Access.READ_ONLY)
	private Set<Role> roles;

	public User() {
	}

	public User(String mail) {
		this(mail, "");
	}

	public User(String mail, String password) {
		this.mail = mail;
		if (password == null) {
			password = "";
		}
		this.password = password;
		setSalt(SaltUtil.generateSaltString());
		this.hashedPassword = encode(password);
	}

	/**
	 * Encodes the raw password. Generally, a good encoding algorithm applies a
	 * SHA-1 or greater hash combined with an 8-byte or greater randomly generated
	 * salt.
	 *
	 * @param rawPassword
	 */
	public String encode(CharSequence rawPassword) {
		MessageDigest d = DigestUtils.getSha256Digest();
		d.update(Base64.decodeBase64(salt));
		d.update(rawPassword.toString().getBytes());
		return Base64.encodeBase64String(d.digest());
	}

	public boolean matches(CharSequence rawPassword) {
		return encode(rawPassword).equals(this.hashedPassword);
	}

	@Column(unique = true)
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", mail=" + mail + "]";
	}

	@Transient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void ensureGeneratingHashedPassword() {
		if (null == this.hashedPassword) {
			setSalt(SaltUtil.generateSaltString());
			this.hashedPassword = encode(password);
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = PASSWORD_COL)
	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public void resetHashedPassword(String rawPassword) {
		setSalt(SaltUtil.generateSaltString());
		this.hashedPassword = encode(rawPassword);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the roles associated with this user
	 *
	 * @return the roles associated with this user
	 */
	@ManyToMany(mappedBy = "users")
	public Set<Role> getRoles() {
		if (roles == null) {
			return Collections.emptySet();
		}
		return roles;
	}

	/**
	 * Gets the roles name String list associated with this user
	 *
	 * @return the roles associated with this user
	 */
	@JsonIgnore
	@Transient
	public Set<String> getRoleNames() {
		if (roles == null) {
			return Collections.emptySet();
		}
		Set<String> roleNames = new HashSet<>();
		roles.stream().forEach(role -> roleNames.add(role.getName()));
		return roleNames;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public boolean containsRole(String roleName) {
		return getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
	}

	/**
	 * Adds a new role to the role set of the user.
	 * 
	 * @param role
	 *            new role of the user
	 */
	public boolean addRole(Role role) {
		if (this.containsRole(role.getName())) {
			return false;
		}
		return roles.add(role);
	}

	public Role getRoleIfExists(String roleName) {
		List<Role> mappedRoles = getRoles().stream().filter(role -> role.getName().equalsIgnoreCase(roleName))
				.collect(Collectors.toList());
		if (mappedRoles.size() > 0) {
			return mappedRoles.get(0);
		} else {
			return null;
		}
	}

	public boolean deleteRole(Role role) {
		Role existingRole = getRoleIfExists(role.getName());
		if (null == existingRole) {
			return false;
		}
		return roles.remove(existingRole);
	}

	@Override
	@Transient
	@JsonIgnore
	public String getName() {
		return mail;
	}
}
