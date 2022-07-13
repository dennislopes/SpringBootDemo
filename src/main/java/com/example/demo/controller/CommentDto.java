package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Comment;

public class CommentDto {
    private Long id;
    private String comment;
    private LocalDateTime commentDate;

    public CommentDto(Comment comment){
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.commentDate = comment.getCommentDate();
    }

    public Long getId() {
        return id;
    }
    public String getComment() {
        return comment;
    }
    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public static List<CommentDto> convert(List<Comment> comments) {
        return comments.stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
