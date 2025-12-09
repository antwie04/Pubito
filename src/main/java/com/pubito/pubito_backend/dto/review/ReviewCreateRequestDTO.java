package com.pubito.pubito_backend.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewCreateRequestDTO(
        @Min(1)
        @Max(5)
        Integer stars,

        String comment
) {
}
