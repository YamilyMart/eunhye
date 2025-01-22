package com.example.yamilymart.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.yamilymart.dto.User;

public interface UserRepository extends JpaRepository<User, String>{
	//SELECT * FROM user WHERE username=?;
	Optional<User> findByUsername(String username);
}
