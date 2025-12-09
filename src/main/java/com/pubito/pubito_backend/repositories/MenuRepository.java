package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByBarId(Long barId);

    List<Menu> findTop3ByBarIdOrderByPriceAsc(Long barId);
}
