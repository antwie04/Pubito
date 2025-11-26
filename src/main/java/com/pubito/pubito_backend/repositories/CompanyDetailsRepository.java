package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, Long> {
}
