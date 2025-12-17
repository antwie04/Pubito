package com.pubito.pubito_backend.dto.bar;

import jakarta.validation.constraints.NotBlank;

public record BarUpdateRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String description
) {
}
