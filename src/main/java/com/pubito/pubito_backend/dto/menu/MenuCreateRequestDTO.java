package com.pubito.pubito_backend.dto.menu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MenuCreateRequestDTO(

        @NotBlank
        String positionName,

        @NotBlank
        String type,

        @NotNull
        @Positive
        BigDecimal price,
        String imgUrl
) {
}
