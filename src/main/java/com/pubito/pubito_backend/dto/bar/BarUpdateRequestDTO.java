package com.pubito.pubito_backend.dto.bar;

import com.pubito.pubito_backend.dto.address.AddressUpdateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsUpdateRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record BarUpdateRequestDTO(
        @NotBlank
        String name,

        @NotBlank
        String description,

        @Valid
        AddressUpdateRequestDTO address,

        @Valid
        CompanyDetailsUpdateRequestDTO companyDetails
) {
}
