package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
    Optional<CompanyDetails> findByBarId(Long barId);

    @Transactional
    void deleteAllByBarId(Long barId);
}
