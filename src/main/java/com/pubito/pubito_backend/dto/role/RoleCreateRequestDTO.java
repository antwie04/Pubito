package com.pubito.pubito_backend.dto.role;

import jakarta.validation.constraints.NotBlank;

public record RoleCreateRequestDTO(
        @NotBlank
        String roleName
) {
}
