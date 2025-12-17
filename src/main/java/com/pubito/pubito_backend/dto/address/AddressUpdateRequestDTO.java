package com.pubito.pubito_backend.dto.address;

import jakarta.validation.constraints.NotBlank;

public record AddressUpdateRequestDTO(
        @NotBlank
        String city,

        @NotBlank
        String googleMapsUrl
) {
}
