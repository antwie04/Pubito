package com.pubito.pubito_backend.dto.menu;

import java.math.BigDecimal;

public record MenuCreateRequestDTO(

        String positionName,
        String type,
        BigDecimal price,
        String imgUrl,
        Long barId
) {
}
