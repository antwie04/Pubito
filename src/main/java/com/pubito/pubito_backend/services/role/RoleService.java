package com.pubito.pubito_backend.services.role;

import com.pubito.pubito_backend.dto.role.RoleCreateRequestDTO;
import com.pubito.pubito_backend.dto.role.RoleResponseDTO;
import com.pubito.pubito_backend.dto.role.RoleUpdateRequestDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleCreateRequestDTO dto);

    RoleResponseDTO getRoleById(Integer id);

    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO updateRole(Integer id, RoleUpdateRequestDTO dto);

    void deleteRole(Integer id);
}
