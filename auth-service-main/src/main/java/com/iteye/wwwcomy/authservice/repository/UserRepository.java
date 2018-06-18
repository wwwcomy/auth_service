package com.iteye.wwwcomy.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteye.wwwcomy.authservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByName(String name);

    User findByNameAndHashedPassword(String name, String password);

    User findByMail(String mail);

}
