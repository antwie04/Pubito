package com.pubito.pubito_backend.dto.address;

public record AddressResponseDTO(
        Long id,
        String city,
        String googleMapsUrl
) {
}
