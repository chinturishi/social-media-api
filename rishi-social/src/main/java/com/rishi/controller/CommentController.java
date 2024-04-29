package com.rishi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishi.config.JwtConstant;
import com.rishi.models.Comment;
import com.rishi.models.Post;
import com.rishi.models.User;
import com.rishi.service.CommentServices;
import com.rishi.service.UserService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentServices commentServices;
	@Autowired
	private UserService userService;

	

	@PostMapping("/create/post/{postId}")
	public ResponseEntity<Comment> createPost(@RequestBody Comment comment , @RequestHeader(JwtConstant.JWT_HEADER) String token,@PathVariable Integer postId) throws Exception {
		User user=userService.findUserFromToken(token);
		Comment createdComment = commentServices.createComment(comment, user.getId(),postId);
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}
	
	@PutMapping("/like/comment/{commentId}")
	public ResponseEntity<Comment>  likeComment(@RequestHeader(JwtConstant.JWT_HEADER) String token,@PathVariable Integer commentId) throws Exception{
		User user=userService.findUserFromToken(token);
		Comment likedComment=commentServices.likeComment(commentId, user.getId());
		return new ResponseEntity<>(likedComment, HttpStatus.OK);
		
	}
}
