package com.pubito.pubito_backend.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressUpdateRequestDTO(
        String city,
        String googleMapsUrl,
        String street,
        Double latitude,
        Double longitude
) {
}
