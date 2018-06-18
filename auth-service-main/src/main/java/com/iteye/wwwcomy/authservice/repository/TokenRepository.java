package com.iteye.wwwcomy.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iteye.wwwcomy.authservice.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {

}
