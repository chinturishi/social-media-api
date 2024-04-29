package com.rishi.service;

import java.util.List;

import com.rishi.models.User;

public interface UserService {
	
	User registerUser(User user);
	
	User findUserById(Integer id) throws Exception;
	
	User findUserByEmail(String email) throws Exception;
	
	User followUser(Integer follwUserId,Integer follwingUserId) throws Exception;
	
	User updateUser(User user,Integer id) throws Exception;
	
	List<User> searchUser(String query);
	
	List<User> findAllUsers();
	
	String deleteUserById(Integer id);
	
	User findUserFromToken(String token) throws Exception;

}
