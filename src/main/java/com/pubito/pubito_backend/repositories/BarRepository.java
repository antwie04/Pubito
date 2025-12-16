package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Bar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarRepository extends JpaRepository<Bar, Long> {
    @Query("""
            SELECT DISTINCT b
            FROM Bar b
            LEFT JOIN b.address a
            WHERE (:city IS NULL OR LOWER(a.city) = LOWER(:city))
              AND (:minAvgRate IS NULL OR b.avgRate >= :minAvgRate)
            ORDER BY b.avgRate DESC
            """)
    List<Bar> findByFiltersOrderByAvgRateDesc(
            @Param("city") String city,
            @Param("minAvgRate") Float minAvgRate
    );

    @Query("""
            SELECT b, MIN(m.price) AS minPrice
            FROM Bar b
            LEFT JOIN b.address a
            LEFT JOIN b.menu m
            WHERE (:city IS NULL OR LOWER(a.city) = LOWER(:city))
              AND (:minAvgRate IS NULL OR b.avgRate >= :minAvgRate)
            GROUP BY b.id
            ORDER BY minPrice ASC
            """)
    List<Object[]> findByFiltersOrderByMinMenuPrice(
            @Param("city") String city,
            @Param("minAvgRate") Float minAvgRate
    );
}
