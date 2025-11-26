package com.pubito.pubito_backend.repositories;

import com.pubito.pubito_backend.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
