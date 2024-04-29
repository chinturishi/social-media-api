package com.rishi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishi.models.Post;
import com.rishi.models.User;
import com.rishi.repository.PostRepository;
import com.rishi.repository.UserRepository;

@Service
public class PostServiceImplementation implements PostServices {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Post createNewPost(Post post, Integer userId) throws Exception {

		if (post != null) {
			post.setCreatedDate(LocalDateTime.now());
			User user = userService.findUserById(userId);
			post.setUser(user);
			if(post.getLiked()==null)
				post.setLiked(new ArrayList<User>());
			return postRepository.save(post);
		}

		return null;
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {

		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post existPost = post.get();
			User user = userService.findUserById(userId);
			if (existPost.getUser().getId() != user.getId()) {
				throw new Exception("This post not belong to the user");
			}
			postRepository.delete(existPost);
		}

		return "Post deleted successfully";
	}

	@Override
	public List<Post> findPostByUserId(Integer userId) {

		return postRepository.findPostByUserId(userId);
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			return post.get();
		}
		throw new Exception("Post not exist with id " + postId);
	}

	@Override
	public List<Post> findAllPosts() {

		return postRepository.findAll();
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws Exception {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post existPost = post.get();
			User user = userService.findUserById(userId);
			if (user.getSavedPosts().contains(existPost)) {
				user.getSavedPosts().remove(existPost);
			} else {
				user.getSavedPosts().add(existPost);
			}
			userRepository.save(user);

			return existPost;
		}
		return null;
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {

		Optional<Post> post = postRepository.findById(postId);
		if (post.isPresent()) {
			Post existPost = post.get();
			User user = userService.findUserById(userId);
			if (existPost.getLiked().contains(user)) {
				existPost.getLiked().remove(user);
			} else {
				existPost.getLiked().add(user);
			}

			return postRepository.save(existPost);
		}

		return null;
	}

}
