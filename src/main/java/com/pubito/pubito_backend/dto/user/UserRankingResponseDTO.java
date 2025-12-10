package com.pubito.pubito_backend.dto.user;

public record UserRankingResponseDTO(
        Long userId,
        String nickname,
        Long reviewsCount,
        String title
) {
}
