package com.rishi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.models.Comment;
import com.rishi.models.Post;
import com.rishi.models.User;
import com.rishi.repository.CommentRepository;
import com.rishi.repository.PostRepository;
import com.rishi.repository.UserRepository;

@Service
public class CommentServiceImplementation implements CommentServices {

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PostServices postServices;
	@Autowired
	private PostRepository postRepository;

	@Override
	public Comment createComment(Comment comment, Integer userId, Integer postId) throws Exception {

		if (comment != null) {
			comment.setCreatedDate(LocalDateTime.now());
			User user = userService.findUserById(userId);
			comment.setUser(user);
			if (comment.getLiked() == null)
				comment.setLiked(new ArrayList<User>());
			comment = commentRepository.save(comment);
			Post post = postServices.findPostById(postId);
			post.getComments().add(comment);
			postRepository.save(post);
			return comment;
		}

		return null;
	}

	@Override
	public Comment likeComment(Integer commentId, Integer likedByUserId) throws Exception {
		Comment comment = findComment(commentId);
		User user = userService.findUserById(likedByUserId);
		if (comment.getLiked().contains(user)) {
			comment.getLiked().remove(user);
		} else
			comment.getLiked().add(user);
		return commentRepository.save(comment);
	}

	@Override
	public Comment findComment(Integer commentId) throws Exception {
		Optional<Comment> commentOptional = commentRepository.findById(commentId);
		if (commentOptional.isPresent()) {
			return commentOptional.get();
		}
		throw new Exception("Comment not exist with id " + commentId);
	}

}
