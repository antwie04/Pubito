package com.pubito.pubito_backend.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Long id,
        @Min(1)
        @Max(5)
        Integer stars,
        String comment,
        Long barId,
        Long userId,
        LocalDateTime createdAt
) {
}
