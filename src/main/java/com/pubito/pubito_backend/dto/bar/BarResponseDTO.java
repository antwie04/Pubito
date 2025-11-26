package com.pubito.pubito_backend.dto.bar;

public record BarResponseDTO(
        Long id,
        String name,
        String description,
        Float avgRate
) {
}
