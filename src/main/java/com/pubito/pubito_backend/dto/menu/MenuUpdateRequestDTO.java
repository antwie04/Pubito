package com.pubito.pubito_backend.dto.menu;

import java.math.BigDecimal;

public record MenuUpdateRequestDTO(

        String positionName,
        String type,
        BigDecimal price,
        String imgUrl
) {
}
