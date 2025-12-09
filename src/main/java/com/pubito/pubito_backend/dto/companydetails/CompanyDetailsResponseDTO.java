package com.pubito.pubito_backend.dto.companydetails;

public record CompanyDetailsResponseDTO(
        Long id,
        Long barId,
        String websiteUrl,
        String phoneNumber
) {
}
