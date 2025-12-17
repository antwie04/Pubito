package com.pubito.pubito_backend.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDTO(
        @Email
        String email,

        @NotBlank
        String password,

        @NotBlank
        String nickname
) {
}
