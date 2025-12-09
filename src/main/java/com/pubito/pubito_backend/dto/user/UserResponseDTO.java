package com.pubito.pubito_backend.dto.user;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String email,
        String nickname,
        Boolean isActive,
        List<String> roles
) {
}
