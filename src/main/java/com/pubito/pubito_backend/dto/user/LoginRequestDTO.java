package com.pubito.pubito_backend.dto.user;

public record LoginRequestDTO(
        String email,
        String password
) {
}
