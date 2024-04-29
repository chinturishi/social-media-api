package com.rishi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishi.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
