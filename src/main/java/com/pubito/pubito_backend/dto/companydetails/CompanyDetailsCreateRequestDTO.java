package com.pubito.pubito_backend.dto.companydetails;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyDetailsCreateRequestDTO(
        @NotBlank
        String websiteUrl,

        @NotNull
        String phoneNumber
) {
}
