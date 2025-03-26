package com.example.simpleblog.adapter.out.repositories;

import com.example.simpleblog.adapter.out.entities.PostEntity;
import com.example.simpleblog.domain.models.Post;
import com.example.simpleblog.domain.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class SpringDataPostRepository  implements PostRepository {

    private final JpaPostRepositoryImpl jpaPostRepository;

    @Override
    public Post save(Post post) {
        return jpaPostRepository.save(PostEntity.toEntity(post))
                .toDomain();
    }


    @Override
    public Optional<Post> findById(Long id) {
        return jpaPostRepository.findById(id)
                .map(PostEntity::toDomain);
    }

}
