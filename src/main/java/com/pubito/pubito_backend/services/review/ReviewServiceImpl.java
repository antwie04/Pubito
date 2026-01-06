package com.pubito.pubito_backend.services.review;

import com.pubito.pubito_backend.dto.review.ReviewCreateRequestDTO;
import com.pubito.pubito_backend.dto.review.ReviewResponseDTO;
import com.pubito.pubito_backend.dto.review.ReviewUpdateRequestDTO;
import com.pubito.pubito_backend.entities.Bar;
import com.pubito.pubito_backend.entities.Review;
import com.pubito.pubito_backend.entities.User;
import com.pubito.pubito_backend.mappers.ReviewMapper;
import com.pubito.pubito_backend.repositories.BarRepository;
import com.pubito.pubito_backend.repositories.ReviewRepository;
import com.pubito.pubito_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BarRepository barRepository;

    @Override
    public ReviewResponseDTO addReview(Long barId, Long userId, ReviewCreateRequestDTO dto) {
        Bar bar = barRepository.findById(barId).orElseThrow(() -> new RuntimeException("bar not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        Review review = Review.builder()
                .user(user)
                .bar(bar)
                .rate(dto.stars())
                .content(dto.comment())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        recalcualteBarAvgRate(bar);
        return reviewMapper.toDTO(review);
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDTO(review);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsForBar(Long barId) {
        return getReviewsForBar(barId, null, null, "newest");
    }

    @Override
    public void deleteReviewById(Long id, Long userId) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("review not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if(!isAdmin && !review.getUser().getId().equals(userId)){
            throw new RuntimeException("you can only delete your own review");
        }

        Bar bar = review.getBar();

        reviewRepository.delete(review);
        recalcualteBarAvgRate(bar);
    }

    @Override
    public ReviewResponseDTO updateReview(Long id, Long userId, ReviewUpdateRequestDTO reviewDTO) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("review not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if(!isAdmin && !review.getUser().getId().equals(userId)){
            throw new RuntimeException("you can update only your review");
        }

        review.setRate(reviewDTO.stars());
        review.setContent(reviewDTO.comment());

        reviewRepository.save(review);
        recalcualteBarAvgRate(review.getBar());
        return reviewMapper.toDTO(review);
    }

    @Override
    public Long countByBarId(Long barId) {
        return reviewRepository.countByBarId(barId);
    }

    @Override
    public List<ReviewResponseDTO> getReviewsForBar(Long barId, Integer stars, String keyword) {
        return getReviewsForBar(barId, stars, keyword, "newest");
    }

    @Override
    public List<ReviewResponseDTO> getReviewsForBar(Long barId, Integer stars, String keyword, String sortBy) {
        if (stars != null && (stars < 1 || stars > 5)) {
            throw new IllegalArgumentException("Niepoprawna liczba gwiazdek");
        }

        String normalizedKeyword = (keyword == null || keyword.trim().isEmpty())
                ? null
                : keyword.trim();

        String normalizedSort = (sortBy == null || sortBy.isBlank())
                ? "newest"
                : sortBy.trim().toLowerCase();

        Sort sort = switch (normalizedSort) {
            case "newest", "najnowsze" ->
                    Sort.by(Sort.Direction.DESC, "createdAt");

            case "oldest", "najstarsze" ->
                    Sort.by(Sort.Direction.ASC, "createdAt");

            case "best", "najlepsze" ->
                    Sort.by(Sort.Direction.DESC, "rate")
                            .and(Sort.by(Sort.Direction.DESC, "createdAt"));

            case "worst", "najsłabsze", "najgorsze" ->
                    Sort.by(Sort.Direction.ASC, "rate")
                            .and(Sort.by(Sort.Direction.DESC, "createdAt"));

            default ->
                    throw new IllegalArgumentException(
                            "Nieznany sort: " + sortBy + ". Dostępne: newest|oldest|best|worst"
                    );
        };

        List<Review> reviews = (stars == null && normalizedKeyword == null)
                ? reviewRepository.findByBarId(barId, sort)
                : reviewRepository.findForBarWithFilters(barId, stars, normalizedKeyword, sort);

        return reviews.stream().map(reviewMapper::toDTO).toList();
    }

    @Override
    public Long countByUserId(Long userId) {
        return reviewRepository.countByUserId(userId);
    }

    private void recalcualteBarAvgRate(Bar bar){
        List<Review> reviews = reviewRepository.findByBarId(bar.getId());
        if(reviews.isEmpty()) {
            bar.setAvgRate(0);
        }else {
            double avg = reviews.stream()
                    .mapToInt(Review::getRate)
                    .average()
                    .orElse(0.0);
            bar.setAvgRate((float)avg);
        }
        barRepository.save(bar);
    }
}
