package com.pubito.pubito_backend.dto.review;

public record ReviewUpdateRequestDTO(
        int stars,
        String comment
) {
}
