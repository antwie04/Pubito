package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByBarId(Long barId);

    List<Menu> findByBarId(Long barId, Sort sort);

    List<Menu> findTop3ByBarIdOrderByPriceAsc(Long barId);

    @Query(value = """
            SELECT m.bar_id, COUNT(*) AS items_count
            FROM menus m
            GROUP BY m.bar_id
            ORDER BY items_count DESC
            """, nativeQuery = true)
    List<Object[]> getBarsByMenuItemsCountDesc();
}
