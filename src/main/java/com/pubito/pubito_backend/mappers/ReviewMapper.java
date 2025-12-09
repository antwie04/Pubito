package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.entities.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }

        Long barId  = review.getBar()  != null ? review.getBar().getId()  : null;
        Long userId = review.getUser() != null ? review.getUser().getId() : null;

        return new ReviewResponseDTO(
                review.getId(),
                review.getRate(),
                review.getContent(),
                barId,
                userId,
                review.getCreatedAt()
        );
    }
}