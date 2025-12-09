package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.menu.MenuCreateRequestDTO;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.dto.menu.MenuUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public MenuResponseDTO toDTO(Menu menu) {
        if (menu == null) {
            return null;
        }

        Long barId = null;
        if (menu.getBar() != null) {
            barId = menu.getBar().getId();
        }

        return new MenuResponseDTO(
                menu.getId(),
                barId,
                menu.getPositionName(),
                menu.getType(),
                menu.getPrice(),
                menu.getImgUrl()
        );
    }


    public Menu toEntity(MenuCreateRequestDTO dto, Bar bar) {
        if (dto == null || bar == null) {
            return null;
        }

        Menu menu = new Menu();
        menu.setBar(bar);
        menu.setPositionName(dto.positionName());
        menu.setType(dto.type());
        menu.setPrice(dto.price());
        menu.setImgUrl(dto.imgUrl());

        return menu;
    }

    public void updateEntity(Menu menu, MenuUpdateRequestDTO dto) {
        if (menu == null || dto == null) {
            return;
        }

        menu.setPositionName(dto.positionName());
        menu.setType(dto.type());
        menu.setPrice(dto.price());
        menu.setImgUrl(dto.imgUrl());
    }
}
