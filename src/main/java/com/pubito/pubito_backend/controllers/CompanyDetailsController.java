package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsCreateRequestDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsResponseDTO;
import com.pubito.pubito_backend.dto.companydetails.CompanyDetailsUpdateRequestDTO;
import com.pubito.pubito_backend.services.companydetails.CompanyDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-details")
public class CompanyDetailsController {

    private final CompanyDetailsService companyDetailsService;

    @PostMapping("/bar/{barId}")
    @PreAuthorize("@permissionService.canModifyBar(#barId)")
    public ResponseEntity<CompanyDetailsResponseDTO> createCompanyDetails(
            @PathVariable Long barId,
            @Valid @RequestBody CompanyDetailsCreateRequestDTO dto
    ) {
        CompanyDetailsResponseDTO response = companyDetailsService.createCompanyDetails(barId, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDetailsResponseDTO> getCompanyDetailsById(
            @PathVariable Long id
    ) {
        CompanyDetailsResponseDTO response = companyDetailsService.getCompanyDetailsById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDetailsResponseDTO>> getAllCompanyDetails() {
        List<CompanyDetailsResponseDTO> response = companyDetailsService.getAllCompanyDetails();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@permissionService.canModifyCompanyDetails(#id)")
    public ResponseEntity<CompanyDetailsResponseDTO> updateCompanyDetails(
            @PathVariable Long id,
            @Valid @RequestBody CompanyDetailsUpdateRequestDTO dto
    ) {
        CompanyDetailsResponseDTO response = companyDetailsService.updateCompanyDetails(id, dto);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionService.canModifyCompanyDetails(#id)")
    public ResponseEntity<Void> deleteCompanyDetails(@PathVariable Long id) {
        companyDetailsService.deleteCompanyDetailsById(id);
        return ResponseEntity.noContent().build();
    }
}
