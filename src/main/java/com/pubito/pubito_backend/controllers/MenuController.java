package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.menu.MenuCreateRequestDTO;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.dto.menu.MenuUpdateRequestDTO;
import com.pubito.pubito_backend.services.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;


    @PostMapping
    public ResponseEntity<MenuResponseDTO> createMenu(@Valid @RequestBody MenuCreateRequestDTO dto) {
        MenuResponseDTO created = menuService.createMenu(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
        List<MenuResponseDTO> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long menuId) {
        MenuResponseDTO menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/bar/{barId}")
    public ResponseEntity<List<MenuResponseDTO>> getMenusByBarId(@PathVariable Long barId) {
        List<MenuResponseDTO> menus = menuService.getMenusByBarId(barId);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponseDTO> updateMenu(
            @PathVariable Long menuId,
            @Valid @RequestBody MenuUpdateRequestDTO dto
    ) {
        MenuResponseDTO updated = menuService.updateMenu(menuId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{barId}/roulette")
    public ResponseEntity<MenuResponseDTO> randomItemFromMenu(@PathVariable Long barId){
        MenuResponseDTO responseDTO = menuService.drawRandomMenuItem(barId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }
}
