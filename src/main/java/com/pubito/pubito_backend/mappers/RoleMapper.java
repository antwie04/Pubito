package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.role.RoleCreateRequestDTO;
import com.pubito.pubito_backend.dto.role.RoleResponseDTO;
import com.pubito.pubito_backend.dto.role.RoleUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponseDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }

        return new RoleResponseDTO(
                role.getId(),
                role.getRoleName()
        );
    }

    public Role toEntity(RoleCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Role role = new Role();
        role.setRoleName(dto.roleName());
        return role;
    }

    public void updateEntity(Role role, RoleUpdateRequestDTO dto) {
        if (role == null || dto == null) {
            return;
        }

        role.setRoleName(dto.roleName());
    }
}
