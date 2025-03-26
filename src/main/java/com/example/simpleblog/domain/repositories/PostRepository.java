package com.example.simpleblog.domain.repositories;

import com.example.simpleblog.domain.models.Post;

import java.util.Optional;

public interface PostRepository {

    Post save(Post post);
    Optional<Post> findById(Long id);

}
