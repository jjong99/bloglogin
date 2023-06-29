package com.sparta.bloglogin.comment.dto;

import com.sparta.bloglogin.comment.entity.Comment;
import com.sparta.bloglogin.post.entity.Post;
import com.sparta.bloglogin.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String username;
    private String content;

    public CommentResponseDto(Long commentId, Long postId, String username, String content) {
        this.commentId = commentId;
        this.postId = postId;
        this.username = username;
        this.content = content;
    }

    public CommentResponseDto(Post post, User user, Comment comment) {
        this.postId = post.getPostId();
        this.username = user.getUsername();
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
    }
}
