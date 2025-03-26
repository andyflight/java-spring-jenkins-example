package com.example.simpleblog.adapter.in.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Jacksonized
public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        String createdAt,
        String updatedAt
) {
}
