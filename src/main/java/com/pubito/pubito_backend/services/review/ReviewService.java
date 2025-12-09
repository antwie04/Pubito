package com.pubito.pubito_backend.services.review;

import com.pubito.pubito_backend.dto.review.ReviewCreateRequestDTO;
import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.dto.review.ReviewUpdateRequestDTO;

import java.util.List;

public interface ReviewService{

    ReviewResponseDTO addReview(Long barId, Long userId,ReviewCreateRequestDTO dto);

    ReviewResponseDTO getReviewById(Long id);

    List<ReviewResponseDTO> getReviewsForBar(Long barId);

    void deleteReviewById(Long id, Long userId);

    ReviewResponseDTO updateReview(Long id, Long userId, ReviewUpdateRequestDTO reviewDTO);

    Long countByBarId(Long barId);

    Long countByUserId(Long userId);

}
