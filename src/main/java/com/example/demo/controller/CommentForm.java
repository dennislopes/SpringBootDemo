package com.example.demo.controller;

import com.example.demo.model.Comment;

public class CommentForm {
    @NotNull
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment convert() {
        return new Comment(comment);
    }
}
