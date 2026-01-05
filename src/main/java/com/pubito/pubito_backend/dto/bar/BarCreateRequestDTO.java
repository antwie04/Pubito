package com.pubito.pubito_backend.dto.bar;

import com.pubito.pubito_backend.dto.address.AddressCreateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record BarCreateRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @Valid
        AddressCreateRequestDTO address,

        @Valid
        CompanyDetailsCreateRequestDTO companyDetails
) {
}
