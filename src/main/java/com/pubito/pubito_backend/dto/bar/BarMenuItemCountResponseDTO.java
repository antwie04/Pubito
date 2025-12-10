package com.pubito.pubito_backend.dto.bar;

public record BarMenuItemCountResponseDTO(
        Long barId,
        String name,
        Long menuItemCount
) {
}
