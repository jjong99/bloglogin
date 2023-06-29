package com.sparta.bloglogin.comment.service;

import com.sparta.bloglogin.comment.dto.CommentRequestDto;
import com.sparta.bloglogin.comment.dto.CommentResponseDto;
import com.sparta.bloglogin.comment.entity.Comment;
import com.sparta.bloglogin.comment.repository.CommentRepository;
import com.sparta.bloglogin.common.code.HanghaeBlogErrorCode;
import com.sparta.bloglogin.common.dto.ApiResult;
import com.sparta.bloglogin.common.exception.HanghaeBlogException;
import com.sparta.bloglogin.common.jwt.JwtUtil;
import com.sparta.bloglogin.post.entity.Post;
import com.sparta.bloglogin.post.repository.PostRepository;
import com.sparta.bloglogin.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest request) {
        User user = this.checkUser(request);

        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_POST, null)
        );

        Comment comment = new Comment();
        comment.setContent(requestDto.getContent());
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentResponseDto(post, user, comment);

    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest request) {
        User user = this.checkUser(request);

        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_POST, null)
        );

        Comment comment = this.findComment(id);

        if(!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.UNAUTHORIZED_USER, null);
        }

        comment.setContent(requestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(post, user, comment);


    }

    @Transactional
    public ApiResult deleteComment(Long id, HttpServletRequest request) {
        User user = this.checkUser(request);

        Comment comment = this.findComment(id);

        if(!user.getUsername().equals(comment.getUser().getUsername())) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.UNAUTHORIZED_USER, null);
        }

        commentRepository.delete(comment);
        return new ApiResult("삭제 완료", HttpStatus.OK.value());
    }

    private User checkUser(HttpServletRequest request) {
        User user = jwtUtil.checkToken(request);
        if (user == null) {
            throw new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_USER, null);
        }
        return user;
    }

    private Comment findComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new HanghaeBlogException(HanghaeBlogErrorCode.NOT_FOUND_COMMENT, null)
        );
        return comment;
    }

}
