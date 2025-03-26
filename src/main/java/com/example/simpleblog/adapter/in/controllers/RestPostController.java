package com.example.simpleblog.adapter.in.controllers;

import com.example.simpleblog.adapter.in.dto.CreatePostRequest;
import com.example.simpleblog.adapter.in.dto.PostResponse;
import com.example.simpleblog.application.usecases.CreatePostUseCase;
import com.example.simpleblog.application.usecases.GetPostByIdUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
@Validated
public class RestPostController {

    private final GetPostByIdUseCase getPostByIdUseCase;
    private final CreatePostUseCase createPostUseCase;

    @PostMapping
    public ResponseEntity<PostResponse> create(@RequestBody @Valid CreatePostRequest createPostRequest) {
        CreatePostUseCase.Input input = CreatePostUseCase.Input.builder()
                .title(createPostRequest.title())
                .content(createPostRequest.content())
                .author(createPostRequest.author())
                .build();

        CreatePostUseCase.Output output = createPostUseCase.execute(input);

        PostResponse postResponse = PostResponse.builder()
                .id(output.getId())
                .title(output.getTitle())
                .content(output.getContent())
                .author(output.getAuthor())
                .createdAt(output.getCreatedAt())
                .updatedAt(output.getUpdatedAt())
                .build();

        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable @Valid Long id) {
        GetPostByIdUseCase.Input input = GetPostByIdUseCase.Input.builder()
                .id(id)
                .build();

        GetPostByIdUseCase.Output output = getPostByIdUseCase.execute(input);

        PostResponse postResponse = PostResponse.builder()
                .id(output.getId())
                .title(output.getTitle())
                .content(output.getContent())
                .author(output.getAuthor())
                .createdAt(output.getCreatedAt())
                .updatedAt(output.getUpdatedAt())
                .build();

        return ResponseEntity.ok(postResponse);
    }
}
