package com.rishi.service;

import org.springframework.stereotype.Service;

import com.rishi.models.Comment;


public interface CommentServices {

	Comment createComment(Comment comment,Integer userId,Integer postId) throws Exception;
	
	Comment likeComment(Integer commentId,Integer likedByUserId) throws Exception;
	
	Comment findComment(Integer commentId) throws Exception;
}
