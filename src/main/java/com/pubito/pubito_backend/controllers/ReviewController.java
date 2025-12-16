package com.pubito.pubito_backend.controllers;

import com.pubito.pubito_backend.dto.review.ReviewCreateRequestDTO;
import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.dto.review.ReviewUpdateRequestDTO;
import com.pubito.pubito_backend.services.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/bars/{barId}/reviews")
    public ResponseEntity<ReviewResponseDTO> addReview(@PathVariable Long barId,
                                                       @RequestParam Long userId,
                                                       @Valid @RequestBody ReviewCreateRequestDTO dto)
    {
        ReviewResponseDTO responseDTO = reviewService.addReview(barId, userId, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id){
        ReviewResponseDTO responseDTO = reviewService.getReviewById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/bars/{barId}/all-reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForBar(@PathVariable("barId") Long barId){
        List<ReviewResponseDTO> reviews = reviewService.getReviewsForBar(barId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/bars/{barId}/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsForBar(
            @PathVariable("barId") Long barId,
            @RequestParam(required = false) Integer stars,
            @RequestParam(required = false) String keyword
    ){
        List<ReviewResponseDTO> reviews = (stars == null && (keyword == null || keyword.isBlank()))
                ? reviewService.getReviewsForBar(barId)
                : reviewService.getReviewsForBar(barId, stars, keyword);

        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/reviews/{id}")
    @PreAuthorize("@permissionService.canModifyReview(#id)")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id,
                                                          @RequestParam Long userId,
                                                          @Valid @RequestBody ReviewUpdateRequestDTO dto)
    {
        ReviewResponseDTO responseDTO = reviewService.updateReview(id, userId, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/reviews/{id}")
    @PreAuthorize("@permissionService.canModifyReview(#id)")
    ResponseEntity<Void> deleteReview(@PathVariable Long id, @RequestParam Long userId)
    {
        reviewService.deleteReviewById(id, userId);
        return ResponseEntity.noContent().build();
    }

}
