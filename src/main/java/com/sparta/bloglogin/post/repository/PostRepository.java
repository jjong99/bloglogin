package com.sparta.bloglogin.post.repository;

import com.sparta.bloglogin.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
