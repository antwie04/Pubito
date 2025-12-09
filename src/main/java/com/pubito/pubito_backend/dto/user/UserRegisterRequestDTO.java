package com.pubito.pubito_backend.dto.user;

public record UserRegisterRequestDTO(
        String email,
        String password,
        String nickname
) {
}
