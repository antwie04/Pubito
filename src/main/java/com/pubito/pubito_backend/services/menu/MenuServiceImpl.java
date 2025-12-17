package com.pubito.pubito_backend.services.menu;

import com.pubito.pubito_backend.dto.menu.MenuCreateRequestDTO;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.dto.menu.MenuUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.Menu;
import com.pubito.pubito_backend.mappers.MenuMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import com.pubito.pubito_backend.repositories.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService{

    private final MenuRepository menuRepository;
    private final BarRepository barRepository;
    private final MenuMapper menuMapper;

    @Override
    public MenuResponseDTO createMenu(MenuCreateRequestDTO dto) {
        Bar bar = barRepository.findById(dto.barId())
                .orElseThrow(() -> new RuntimeException("bar not found" + dto.barId()));

        Menu menu = menuMapper.toEntity(dto, bar);
        Menu saved = menuRepository.save(menu);

        return menuMapper.toDTO(saved);

    }

    @Override
    public MenuResponseDTO getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("menu not found: " + menuId));

        return menuMapper.toDTO(menu);
    }

    @Override
    public List<MenuResponseDTO> getAllMenus() {
        return menuRepository.findAll()
                .stream()
                .map(menuMapper::toDTO)
                .toList();
    }

    @Override
    public List<MenuResponseDTO> getMenusByBarId(Long barId) {
        return menuRepository.findByBarId(barId)
                .stream()
                .map(menuMapper::toDTO)
                .toList();
    }

    @Override
    public MenuResponseDTO updateMenu(Long menuId, MenuUpdateRequestDTO dto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("menu not found with id: " + menuId));

        menuMapper.updateEntity(menu, dto);
        Menu saved = menuRepository.save(menu);

        return menuMapper.toDTO(saved);
    }

    @Override
    public List<MenuResponseDTO> getTop3CheapestByBarId(Long barId) {
        List<Menu> menus = menuRepository.findTop3ByBarIdOrderByPriceAsc(barId);
        return menus.stream()
                .map(menuMapper::toDTO)
                .toList();
    }

    @Override
    public MenuResponseDTO drawRandomMenuItem(Long barId) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new RuntimeException("bar does not exist"));

        List<Menu> menuItems = menuRepository.findByBarId(barId);
        if(menuItems.isEmpty()){
            throw new RuntimeException("no menu items");
        }

        int randomIndex = (int)(Math.random() * menuItems.size());
        Menu randomItem = menuItems.get(randomIndex);

        return menuMapper.toDTO(randomItem);

    }

    @Override
    public List<MenuResponseDTO> getMenusForBarSorted(Long barId, String sortBy, String direction){
        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction.fromString(direction);
        } catch (IllegalArgumentException e) {
            sortDirection = Sort.Direction.ASC;
        }

        Sort sort;

        if ("category".equalsIgnoreCase(sortBy)) {
            sort = Sort.by(sortDirection, "category").and(Sort.by(Sort.Direction.ASC, "price"));
        } else {

            sort = Sort.by(sortDirection, "price");
        }

        List<Menu> menus = menuRepository.findByBarId(barId, sort);

        return menus.stream()
                .map(menuMapper::toDTO)
                .toList();


    }

    @Override
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + menuId));

        menuRepository.delete(menu);
    }
}
