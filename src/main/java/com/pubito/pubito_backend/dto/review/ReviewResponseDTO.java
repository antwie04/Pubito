package com.pubito.pubito_backend.dto.review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Long id,
        Integer stars,
        String comment,
        Long barId,
        Long userId,
        LocalDateTime createdAt
) {
}
