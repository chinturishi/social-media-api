package com.rishi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rishi.config.JwtProvider;
import com.rishi.config.JwtValidator;
import com.rishi.models.User;
import com.rishi.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		if(user.getFollowers()==null)
			user.setFollowers(new ArrayList<Integer>());
		if(user.getFollowings()==null)
			user.setFollowings(new ArrayList<Integer>());
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User findUserById(Integer id) throws Exception {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new Exception("User not exist with id " + id);
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		Optional<User> user= userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		}
		throw new Exception("User not exist with email " + email);
	}

	@Override
	public User followUser(Integer follwUserId, Integer follwingUserId) throws Exception {
		User follwerUser= findUserById(follwUserId);
		User follwingUser= findUserById(follwingUserId);
		
		follwingUser.getFollowers().add(follwerUser.getId());
		follwerUser.getFollowings().add(follwingUser.getId());
		userRepository.save(follwerUser);
		userRepository.save(follwingUser);
		return follwerUser;
	}

	@Override
	public User updateUser(User user,Integer id) throws Exception {
		if (userRepository.existsById(id)) {
			Optional<User> userdb = userRepository.findById(id);
			User existUser = userdb.get();
			if (user.getFirstName() != null)
				existUser.setFirstName(user.getFirstName());
			if (user.getLastName() != null)
				existUser.setLastName(user.getLastName());
			if (user.getEmail() != null)
				existUser.setEmail(user.getFirstName());
			if (user.getPassword() != null)
				existUser.setPassword(user.getPassword());
			if (user.getGender() != null)
				existUser.setGender(user.getGender());
			return userRepository.save(existUser);

		}
		throw new Exception("User not exist with id " + id);
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepository.searchUser(query);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String deleteUserById(Integer id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
			return "User deleted successfilly with id " + id;
		}
		return "User not found with id " + id;
	}

	@Override
	public User findUserFromToken(String token) throws Exception {
		JwtProvider jwtProvider=new JwtProvider();
		String email=jwtProvider.getEmailFromJwtToken(token);
		return findUserByEmail(email);
	}

}
