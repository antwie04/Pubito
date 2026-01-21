package com.pubito.pubito_backend.dto.user;

import jakarta.validation.constraints.Email;

public record UserUpdateRequestDTO(
        @Email
        String email,
        String nickname
) {
}
