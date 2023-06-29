package com.sparta.bloglogin.comment.repository;

import com.sparta.bloglogin.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
