package com.anant.rest.services.restfulwebservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anant.rest.services.restfulwebservices.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
