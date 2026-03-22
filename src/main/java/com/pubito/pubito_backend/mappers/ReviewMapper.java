package com.pubito.pubito_backend.mappers;

import com.pubito.pubito_backend.dto.review.ReviewCreateRequestDTO;
import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.Review;
import com.pubito.pubito_backend.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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


    public Review toEntity(ReviewCreateRequestDTO dto, User user, Bar bar) {
        if (dto == null) {
            return null;
        }

        return Review.builder()
                .user(user)
                .bar(bar)
                .rate(dto.stars())
                .content(dto.comment())
                .createdAt(LocalDateTime.now())
                .build();
    }
}