package com.sparta.bloglogin.post.controller;

import com.sparta.bloglogin.common.dto.ApiResult;
import com.sparta.bloglogin.post.dto.PostRequestDto;
import com.sparta.bloglogin.post.dto.PostResponseDto;
import com.sparta.bloglogin.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    public PostResponseDto CreatePost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(id, requestDto, request);
    }

    @DeleteMapping("/post/{id}")
    public ApiResult deletePost(@PathVariable Long id, HttpServletRequest request) {
        postService.deletePost(id, request);
        return new ApiResult("게시글 삭제 성공", HttpStatus.OK.value());
    }
}
