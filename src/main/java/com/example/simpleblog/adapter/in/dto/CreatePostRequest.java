package com.example.simpleblog.adapter.in.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import jakarta.validation.constraints.*;

@Jacksonized
@Builder
public record CreatePostRequest(

        @NotBlank(message = "Post title cannot be blank")
        @Size(min= 3, max = 100, message = "Post title cannot exceed 100 characters and be less than 3 characters")
        String title,

        @NotBlank(message = "Post content cannot be blank")
        @Size(min= 10, max = 1000, message = "Post content cannot exceed 1000 characters and be less than 10 characters")
        String content,

        @NotBlank(message = "Post author cannot be blank")
        @Size(min= 3, max = 50, message = "Post author cannot exceed 50 characters and be less than 3 characters")
        String author
) {
}
