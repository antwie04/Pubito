package com.pubito.pubito_backend.services.companydetails;

import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsResponseDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsUpdateRequestDTO;

import java.util.List;

public interface CompanyDetailsService {

    CompanyDetailsResponseDTO createCompanyDetails(Long barId, CompanyDetailsCreateRequestDTO dto);

    CompanyDetailsResponseDTO getCompanyDetailsById(Long id);

    List<CompanyDetailsResponseDTO> getAllCompanyDetails();

    CompanyDetailsResponseDTO updateCompanyDetails(Long id, CompanyDetailsUpdateRequestDTO dto);

    void deleteCompanyDetailsById(Long id);
}