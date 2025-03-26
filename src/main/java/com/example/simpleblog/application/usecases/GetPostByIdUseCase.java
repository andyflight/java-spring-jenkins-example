package com.example.simpleblog.application.usecases;

import com.example.simpleblog.application.AbstractUseCase;
import com.example.simpleblog.domain.models.Post;
import com.example.simpleblog.domain.repositories.PostRepository;
import com.example.simpleblog.shared.PostNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetPostByIdUseCase extends AbstractUseCase<GetPostByIdUseCase.Input, GetPostByIdUseCase.Output> {

    private final PostRepository postRepository;

    @Override
    public Output execute(Input input) {
        Post foundPost  = postRepository.findById(input.getId()).orElseThrow(() -> new PostNotFoundException(String.valueOf(input.getId())));

        return Output.builder()
                .id(foundPost.getId())
                .title(foundPost.getTitle())
                .content(foundPost.getContent())
                .author(foundPost.getAuthor())
                .createdAt(foundPost.getCreatedAt())
                .updatedAt(foundPost.getUpdatedAt())
                .build();
    }

    @Builder(toBuilder = true)
    @Value
    public static class Input {
        Long id;
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
