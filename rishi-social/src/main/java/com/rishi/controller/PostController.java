package com.rishi.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.rishi.config.JwtConstant;
import com.rishi.models.Post;
import com.rishi.models.User;
import com.rishi.response.ApiResponse;
import com.rishi.service.PostServices;
import com.rishi.service.UserService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostServices postServices;
	
	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader(JwtConstant.JWT_HEADER) String token) throws Exception {
		User user=userService.findUserFromToken(token);
		Post createdPost = postServices.createNewPost(post, user.getId());
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId, @RequestHeader(JwtConstant.JWT_HEADER) String token)
			throws Exception {
		User user=userService.findUserFromToken(token);
		String message = postServices.deletePost(postId, user.getId());
		ApiResponse response=new ApiResponse(message, true);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{postId}")
	public  ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws Exception{
		Post post =postServices.findPostById(postId);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Post>> findUserPost(@RequestHeader(JwtConstant.JWT_HEADER) String token ) throws Exception{
		User user=userService.findUserFromToken(token);
		List<Post> posts=postServices.findPostByUserId(user.getId());
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<List<Post>> findAllPosts( ){
		
		List<Post> posts=postServices.findAllPosts();
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	@PutMapping("/save/{postId}")
	public ResponseEntity<Post> savePost(@PathVariable Integer postId, @RequestHeader(JwtConstant.JWT_HEADER) String token) throws Exception{
		User user=userService.findUserFromToken(token);
		Post post=postServices.savedPost(postId, user.getId());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	@PutMapping("/like/{postId}")
	public ResponseEntity<Post> likePost(@PathVariable Integer postId, @RequestHeader(JwtConstant.JWT_HEADER) String token) throws Exception{
		User user=userService.findUserFromToken(token);
		Post post=postServices.likePost(postId, user.getId());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

}
