package com.anant.testcom.service;

import com.anant.testcom.exception.UserException;
import com.anant.testcom.model.User;

public interface UserService {

public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
}
