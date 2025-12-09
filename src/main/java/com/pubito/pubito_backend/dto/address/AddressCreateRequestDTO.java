package com.pubito.pubito_backend.dto.address;

public record AddressCreateRequestDTO(
        String city,
        String googleMapsUrl
) {
}
