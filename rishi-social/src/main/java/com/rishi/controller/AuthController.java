package com.rishi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.config.JwtProvider;
import com.rishi.models.User;
import com.rishi.request.LoginRequest;
import com.rishi.response.AuthResponse;
import com.rishi.service.CustomUserDetailService;
import com.rishi.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(("/signup"))
	public AuthResponse createUser(@RequestBody User user) {
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User updatedUser = userService.registerUser(user);
		Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUser.getEmail(), updatedUser.getPassword());
		String token = new JwtProvider().generateToken(authentication);

		AuthResponse authResponse = new AuthResponse(token, "Register Sucess");

		return authResponse;
	}

	@PostMapping(("/signin"))
	public AuthResponse signIn(@RequestBody LoginRequest loginRequest) {

		UserDetails userDetail = customUserDetailService.loadUserByUsername(loginRequest.getEmail());
		if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), userDetail.getPassword())) {
			throw new BadCredentialsException("Invalid username or password.");
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
				userDetail.getAuthorities());
		String token = new JwtProvider().generateToken(authentication);
		AuthResponse authResponse = new AuthResponse(token, "Login Sucess");
		return authResponse;
	}

}
