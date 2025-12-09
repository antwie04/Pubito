package com.pubito.pubito_backend.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewUpdateRequestDTO(
        @Min(1)
        @Max(5)
        Integer stars,

        String comment
) {
}
