package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
    Optional<CompanyDetails> findByBarId(Long barId);
}
