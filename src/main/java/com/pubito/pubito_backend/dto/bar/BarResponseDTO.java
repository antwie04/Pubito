package com.pubito.pubito_backend.dto.bar;

import com.pubito.pubito_backend.dto.address.AddressResponseDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsResponseDTO;

public record BarResponseDTO(
        Long id,
        String name,
        String description,
        AddressResponseDTO address,
        CompanyDetailsResponseDTO companyDetails,
        Float avgRate
) {
}
