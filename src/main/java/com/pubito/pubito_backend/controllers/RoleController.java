package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.role.RoleCreateRequestDTO;
import com.pubito.pubito_backend.dto.role.RoleResponseDTO;
import com.pubito.pubito_backend.dto.role.RoleUpdateRequestDTO;
import com.pubito.pubito_backend.services.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {


    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody RoleCreateRequestDTO dto) {
        RoleResponseDTO response = roleService.createRole(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Integer id) {
        RoleResponseDTO response = roleService.getRoleById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable Integer id,
            @RequestBody RoleUpdateRequestDTO dto
    ) {
        RoleResponseDTO response = roleService.updateRole(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }

}
