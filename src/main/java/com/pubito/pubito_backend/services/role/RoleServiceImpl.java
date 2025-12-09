package com.pubito.pubito_backend.services.role;

import com.pubito.pubito_backend.dto.role.RoleCreateRequestDTO;
import com.pubito.pubito_backend.dto.role.RoleResponseDTO;
import com.pubito.pubito_backend.dto.role.RoleUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Role;
import com.pubito.pubito_backend.mappers.RoleMapper;
import com.pubito.pubito_backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponseDTO createRole(RoleCreateRequestDTO dto) {
        Role role = roleMapper.toEntity(dto);
        roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    @Override
    public RoleResponseDTO getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("role not found"));
        return roleMapper.toDTO(role);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDTO)
                .toList();
    }

    @Override
    public RoleResponseDTO updateRole(Integer id, RoleUpdateRequestDTO dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("role not found"));

        roleMapper.updateEntity(role, dto);
        roleRepository.save(role);

        return roleMapper.toDTO(role);
    }

    @Override
    public void deleteRole(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("role not found"));
        roleRepository.delete(role);
    }
}
