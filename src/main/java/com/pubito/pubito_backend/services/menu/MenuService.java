package com.pubito.pubito_backend.services.menu;

import com.pubito.pubito_backend.dto.menu.MenuCreateRequestDTO;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.dto.menu.MenuUpdateRequestDTO;

import java.util.List;

public interface MenuService {
    MenuResponseDTO createMenu(MenuCreateRequestDTO dto);

    MenuResponseDTO getMenuById(Long menuId);

    List<MenuResponseDTO> getAllMenus();

    List<MenuResponseDTO> getMenusByBarId(Long barId);

    MenuResponseDTO updateMenu(Long menuId, MenuUpdateRequestDTO dto);

    List<MenuResponseDTO> getTop3CheapestByBarId(Long barId);

    List<MenuResponseDTO> getMenusForBarSorted(Long barId, String sortBy, String direction);

    MenuResponseDTO drawRandomMenuItem(Long barId);

    void deleteMenu(Long menuId);
}
