package com.pubito.pubito_backend.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressCreateRequestDTO(
        @NotBlank
        String city,

        @NotBlank
        String googleMapsUrl
) {
}
