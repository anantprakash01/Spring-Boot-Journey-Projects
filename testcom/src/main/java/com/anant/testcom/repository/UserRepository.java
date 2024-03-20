package com.anant.testcom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anant.testcom.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
