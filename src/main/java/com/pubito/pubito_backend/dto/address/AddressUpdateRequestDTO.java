package com.pubito.pubito_backend.dto.address;

public record AddressUpdateRequestDTO(
        String city,
        String googleMapsUrl
) {
}
