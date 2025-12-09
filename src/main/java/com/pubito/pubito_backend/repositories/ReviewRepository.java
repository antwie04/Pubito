package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBarId(Long barId);

    Long countByBarId(Long barId);

    Long countByUserId(Long userId);
}
