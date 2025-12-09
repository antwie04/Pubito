package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsResponseDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.CompanyDetails;
import org.springframework.stereotype.Component;

@Component
public class CompanyDetailsMapper {
    public CompanyDetailsResponseDTO toDTO(CompanyDetails companyDetails) {
        if (companyDetails == null) {
            return null;
        }

        Long barId = null;
        if (companyDetails.getBar() != null) {
            barId = companyDetails.getBar().getId();
        }

        return new CompanyDetailsResponseDTO(
                companyDetails.getId(),
                barId,
                companyDetails.getWebsiteUrl(),
                companyDetails.getPhoneNumber()
        );
    }

    public CompanyDetails toEntity(CompanyDetailsCreateRequestDTO dto, Bar bar) {
        if (dto == null || bar == null) {
            return null;
        }

        CompanyDetails companyDetails = new CompanyDetails();
        companyDetails.setBar(bar);
        companyDetails.setWebsiteUrl(dto.websiteUrl());
        companyDetails.setPhoneNumber(dto.phoneNumber());

        return companyDetails;
    }

    public void updateEntity(CompanyDetails companyDetails, CompanyDetailsUpdateRequestDTO dto) {
        if (companyDetails == null || dto == null) {
            return;
        }

        companyDetails.setWebsiteUrl(dto.websiteUrl());
        companyDetails.setPhoneNumber(dto.phoneNumber());
    }
}
