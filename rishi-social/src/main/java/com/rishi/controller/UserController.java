package com.rishi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.config.JwtConstant;
import com.rishi.models.Post;
import com.rishi.models.User;
import com.rishi.repository.UserRepository;
import com.rishi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// Get All users
	@GetMapping("/")
	public List<User> getUsers() {
		return userService.findAllUsers();
	}

//	@GetMapping("/api/users/{userId}")
//	public User getUserById(@PathVariable("userId") Integer id) throws Exception {
//		return userService.findUserById(id);
//
//	}
//	
	@GetMapping("/email/{email}")
	public User getUserByEmail(@PathVariable String email) throws Exception {
		return userService.findUserByEmail(email);

	}

	@GetMapping("/search")
	public List<User> searchUsers(@RequestParam("query") String query) throws Exception {
		return userService.searchUser(query);

	}

	@PutMapping("/update")
	public User updateUser(@RequestBody User updateUser, @RequestHeader(JwtConstant.JWT_HEADER) String token)
			throws Exception {
		User user = userService.findUserFromToken(token);
		return userService.updateUser(updateUser, user.getId());
	}

	@PutMapping("/follow/{follwingUserId}")
	public User updatefollowUser(@PathVariable Integer follwingUserId,
			@RequestHeader(JwtConstant.JWT_HEADER) String token) throws Exception {
		User user = userService.findUserFromToken(token);
		return userService.followUser(user.getId(), follwingUserId);
	}

	@DeleteMapping("/delete")
	public String deleteUser(@RequestHeader(JwtConstant.JWT_HEADER) String token) throws Exception {
		User user = userService.findUserFromToken(token);
		return userService.deleteUserById(user.getId());
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getUserFromJwtToke(@RequestHeader(JwtConstant.JWT_HEADER) String token)
			throws Exception {
		User user = userService.findUserFromToken(token);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
