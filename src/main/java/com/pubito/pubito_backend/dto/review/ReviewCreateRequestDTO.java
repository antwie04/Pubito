package com.pubito.pubito_backend.dto.review;

public record ReviewCreateRequestDTO(
        int stars,
        String comment
) {
}
