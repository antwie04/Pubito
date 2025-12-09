package com.pubito.pubito_backend.services.companydetails;

import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsResponseDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.CompanyDetails;
import com.pubito.pubito_backend.mappers.CompanyDetailsMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import com.pubito.pubito_backend.repositories.CompanyDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyDetailsServiceImpl implements CompanyDetailsService{

    private final CompanyDetailsRepository companyDetailsRepository;
    private final BarRepository barRepository;
    private final CompanyDetailsMapper companyDetailsMapper;

    @Override
    public CompanyDetailsResponseDTO createCompanyDetails(Long barId, CompanyDetailsCreateRequestDTO dto) {
        Bar bar = barRepository.findById(barId)
                .orElseThrow(() -> new RuntimeException("bar not found"));

        CompanyDetails companyDetails = companyDetailsMapper.toEntity(dto, bar);

        companyDetailsRepository.save(companyDetails);

        bar.setCompanyDetails(companyDetails);
        barRepository.save(bar);

        return companyDetailsMapper.toDTO(companyDetails);
    }

    @Override
    public CompanyDetailsResponseDTO getCompanyDetailsById(Long id) {
        CompanyDetails companyDetails = companyDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("company details not found"));

        return companyDetailsMapper.toDTO(companyDetails);
    }

    @Override
    public List<CompanyDetailsResponseDTO> getAllCompanyDetails() {
        return companyDetailsRepository.findAll().stream()
                .map(companyDetailsMapper::toDTO)
                .toList();
    }

    @Override
    public CompanyDetailsResponseDTO updateCompanyDetails(Long id, CompanyDetailsUpdateRequestDTO dto) {
        CompanyDetails companyDetails = companyDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("company details not found"));

        companyDetailsMapper.updateEntity(companyDetails, dto);

        CompanyDetails updated = companyDetailsRepository.save(companyDetails);
        return companyDetailsMapper.toDTO(updated);
    }

    @Override
    public void deleteCompanyDetailsById(Long id) {
        CompanyDetails companyDetails = companyDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("company details not found"));

        barRepository.findAll().stream()
                .filter(bar -> bar.getCompanyDetails() != null &&
                        bar.getCompanyDetails().getId().equals(id))
                .forEach(bar -> {
                    bar.setCompanyDetails(null);
                    barRepository.save(bar);
                });

        companyDetailsRepository.delete(companyDetails);
    }
}
