package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;

public class UpdateCommentForm {
    @NotNull @NotEmpty
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment update(Long id, CommentRepository commentRepository) {
        Comment comment = commentRepository.getReferenceById(id);
        comment.setComment(this.comment);
        return comment;
    }
}
