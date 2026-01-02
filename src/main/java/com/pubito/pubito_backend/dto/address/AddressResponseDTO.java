package com.pubito.pubito_backend.dto.address;

public record AddressResponseDTO(
        Long id,
        String street,
        String city,
        String googleMapsUrl,
        Double latitude,
        Double longitude
) {
}
