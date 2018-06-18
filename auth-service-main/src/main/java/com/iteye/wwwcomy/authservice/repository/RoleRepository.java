package com.iteye.wwwcomy.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteye.wwwcomy.authservice.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

	Role findByName(String name);

}
