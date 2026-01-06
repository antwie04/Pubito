package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.menu.MenuCreateRequestDTO;
import com.pubito.pubito_backend.dto.menu.MenuResponseDTO;
import com.pubito.pubito_backend.dto.menu.MenuUpdateRequestDTO;
import com.pubito.pubito_backend.services.menu.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuService menuService;


    @PostMapping("/bars/{barId}/menus")
    @PreAuthorize("@permissionService.canModifyBar(#barId)")
    public ResponseEntity<MenuResponseDTO> createMenu(@PathVariable Long barId, @Valid @RequestBody MenuCreateRequestDTO dto) {
        MenuResponseDTO created = menuService.createMenu(barId, dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuResponseDTO>> getAllMenus() {
        List<MenuResponseDTO> menus = menuService.getAllMenus();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuResponseDTO> getMenuById(@PathVariable Long menuId) {
        MenuResponseDTO menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/bars/{barId}/menus/all")
    public ResponseEntity<List<MenuResponseDTO>> getMenusByBarId(@PathVariable Long barId) {
        List<MenuResponseDTO> menus = menuService.getMenusByBarId(barId);
        return ResponseEntity.ok(menus);
    }

    @PutMapping("/menus/{menuId}")
    @PreAuthorize("@permissionService.canModifyMenu(#menuId)")
    public ResponseEntity<MenuResponseDTO> updateMenu(
            @PathVariable Long menuId,
            @Valid @RequestBody MenuUpdateRequestDTO dto
    ) {
        MenuResponseDTO updated = menuService.updateMenu(menuId, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/bars/{barId}/menus/roulette")
    public ResponseEntity<MenuResponseDTO> randomItemFromMenu(@PathVariable Long barId){
        MenuResponseDTO responseDTO = menuService.drawRandomMenuItem(barId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/bars/{barId}/menus")
    public ResponseEntity<List<MenuResponseDTO>> getMenusForBar(
            @PathVariable Long barId,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        List<MenuResponseDTO> menus = menuService.getMenusForBarSorted(barId, direction, sortBy);
        return ResponseEntity.ok(menus);
    }

    @DeleteMapping("/menus/{menuId}")
    @PreAuthorize("@permissionService.canModifyMenu(#menuId)")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }
}
