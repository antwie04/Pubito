package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBarId(Long barId);

    Long countByBarId(Long barId);

    Long countByUserId(Long userId);

    @Query(value = """
            Select r.user_id, COUNT(*) AS review_count
            FROM reviews r
            GROUP BY r.user_id
            ORDER BY review_count DESC
            """, nativeQuery = true)
    List<Object[]> getUserReviewRanking();


    @Query(value = """
            SELECT r.bar_id, COUNT(*) AS review_count
                        FROM reviews r
                        WHERE r.created_at >= DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 7 DAY)
                        GROUP BY r.bar_id
                        ORDER BY review_count DESC
            """, nativeQuery = true)
    List<Object[]> getTrendyBarsLastWeek();
}
