package com.example.simpleblog.domain.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Post {

    Long id;
    String title;
    String content;
    String author;
    String createdAt;
    String updatedAt;

}
