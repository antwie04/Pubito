package com.pubito.pubito_backend.dto.menu;

import java.math.BigDecimal;

public record MenuResponseDTO(

        Long menuId,
        Long barId,
        String positionName,
        String type,
        BigDecimal price,
        String imgUrl
) {
}
