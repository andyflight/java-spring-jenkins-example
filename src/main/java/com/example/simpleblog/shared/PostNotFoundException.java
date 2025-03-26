package com.example.simpleblog.shared;

public class PostNotFoundException extends RuntimeException {

    public static final String PostNotFoundMessageTemplate = "Category with id %s not found";

    public PostNotFoundException(String postId) {
        super(String.format(PostNotFoundMessageTemplate, postId));
    }
}
