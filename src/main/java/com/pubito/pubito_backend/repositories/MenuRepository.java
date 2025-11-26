package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
