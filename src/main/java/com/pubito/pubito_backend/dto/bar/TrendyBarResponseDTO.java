package com.pubito.pubito_backend.dto.bar;

public record TrendyBarResponseDTO(
        Long barId,
        String name,
        Long reviewsCount
) {
}
