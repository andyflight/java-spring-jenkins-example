package com.example.simpleblog.adapter.out.entities;

import com.example.simpleblog.domain.models.Post;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;


    public Post toDomain() {
        return Post.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .author(this.author)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public static PostEntity toEntity(Post post) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(post.getId());
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setAuthor(post.getAuthor());
        postEntity.setCreatedAt(post.getCreatedAt());
        postEntity.setUpdatedAt(post.getUpdatedAt());
        return postEntity;
    }
}



