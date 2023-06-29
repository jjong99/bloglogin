package com.sparta.bloglogin.post.service;

import com.sparta.bloglogin.comment.dto.CommentResponseDto;
import com.sparta.bloglogin.comment.entity.Comment;
import com.sparta.bloglogin.common.code.HanghaeBlogErrorCode;
import com.sparta.bloglogin.common.exception.HanghaeBlogException;
import com.sparta.bloglogin.common.jwt.JwtUtil;
import com.sparta.bloglogin.post.dto.PostRequestDto;
import com.sparta.bloglogin.post.dto.PostResponseDto;
import com.sparta.bloglogin.post.entity.Post;
import com.sparta.bloglogin.post.repository.PostRepository;
import com.sparta.bloglogin.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        User user = jwtUtil.checkToken(request);
        if (user == null) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
        }

        Post post = new Post(requestDto, user);

        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();

    }


    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        PostResponseDto responseDto = new PostResponseDto(post);
        return responseDto;
    }


    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {
        User user = jwtUtil.checkToken(request);
        if (user == null) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
        }

        Post post = findPost(id);

        if (!post.getUser().equals(user)) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.UNAUTHORIZED_USER, null);
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, HttpServletRequest request) {
        User user = jwtUtil.checkToken(request);
        if (user == null) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
        }

        Post post = findPost(id);

        if (post.getUser().equals(user)) {
            postRepository.delete(post);
        }


    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }


}
