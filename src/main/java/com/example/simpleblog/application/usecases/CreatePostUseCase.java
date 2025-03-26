package com.example.simpleblog.application.usecases;

import com.example.simpleblog.application.AbstractUseCase;
import com.example.simpleblog.domain.models.Post;
import com.example.simpleblog.domain.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CreatePostUseCase extends AbstractUseCase<CreatePostUseCase.Input, CreatePostUseCase.Output> {

    private final PostRepository postRepository;

    @Override
    public Output execute(Input input) {

        Post post = Post.builder()
                .title(input.getTitle())
                .content(input.getContent())
                .author(input.getAuthor())
                .createdAt(String.valueOf(System.currentTimeMillis()))
                .updatedAt(String.valueOf(System.currentTimeMillis()))
                .build();

        Post savedPost = postRepository.save(post);

        return Output.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .author(savedPost.getAuthor())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }


    @Builder(toBuilder = true)
    @Value
    public static class Input {
        String title;
        String content;
        String author;
    }

    @Builder(toBuilder = true)
    @Value
    public static class Output {
        Long id;
        String title;
        String content;
        String author;
        String createdAt;
        String updatedAt;
    }
}
