package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Bar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarRepository extends JpaRepository<Bar, Long> {
}
