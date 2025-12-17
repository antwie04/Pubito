package com.pubito.pubito_backend.dto.role;

import jakarta.validation.constraints.NotBlank;

public record RoleUpdateRequestDTO(
        @NotBlank
        String roleName
) {
}
