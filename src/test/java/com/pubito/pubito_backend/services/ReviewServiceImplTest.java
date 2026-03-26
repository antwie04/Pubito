package com.pubito.pubito_backend.services;

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
import com.pubito.pubito_backend.services.review.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {


    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BarRepository barRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;


    @Test
    void shouldAddReviewAndRecalculateBarAverage() {
        // given
        Long barId = 1L;
        Long userId = 2L;

        User user = User.builder()
                .id(userId)
                .email("user@pubito.pl")
                .password("pass")
                .nickname("userrr")
                .isActive(true)
                .build();

        Bar bar = Bar.builder()
                .id(barId)
                .name("Pub")
                .avgRate(0.0f)
                .owner(user)
                .build();

        ReviewCreateRequestDTO dto = new ReviewCreateRequestDTO(
                5,
                "fantastic place"
        );

        Review review = Review.builder()
                .bar(bar)
                .user(user)
                .rate(5)
                .content("fantastic place")
                .build();

        ReviewResponseDTO responseDTO = new ReviewResponseDTO(
                1L,
                5,
                "fantastic place",
                barId,
                userId,
                null


        );

        Review existingReview = Review.builder()
                .bar(bar)
                .user(user)
                .rate(3)
                .content("ok")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(barRepository.findById(barId)).thenReturn(Optional.of(bar));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reviewRepository.findByBarId(barId)).thenReturn(List.of(review, existingReview));
        when(reviewMapper.toDTO(any(Review.class))).thenReturn(responseDTO);

        ReviewResponseDTO result = reviewService.addReview(barId, userId, dto);

        assertEquals(5, result.stars());
        assertEquals("fantastic place", result.comment());
        assertEquals(4.0f, bar.getAvgRate());

        verify(reviewRepository).save(any(Review.class));
        verify(barRepository).save(bar);
    }

    @Test
    void shouldDeleteReviewByAuthor(){
        Long userId = 1L;
        Long reviewId = 1L;

        User user = User.builder()
                .id(userId)
                .email("user@test.pl")
                .isActive(true)
                .nickname("ussr")
                .password("password")
                .build();

        Bar bar = Bar.builder()
                .id(3L)
                .name("pub")
                .avgRate(4.0f)
                .owner(user)
                .build();

        Review review = Review.builder()
                .id(reviewId)
                .user(user)
                .bar(bar)
                .rate(5)
                .content("great bar")
                .build();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.findByBarId(bar.getId())).thenReturn(List.of());

        reviewService.deleteReviewById(reviewId, userId);

        verify(reviewRepository).delete(review);
        verify(barRepository).save(bar);

    }

    @Test
    void shouldThrowWhenDeletingReviewByNotTheAuthor(){

        Long reviewId = 1L;

        User author = User.builder()
                .id(3L)
                .email("user@test.pl")
                .isActive(true)
                .nickname("ussr")
                .password("password")
                .build();

        User nonAuthor = User.builder()
                .id(5L)
                .email("user@test.pl")
                .isActive(true)
                .nickname("ussr")
                .password("password")
                .build();

        Bar bar = Bar.builder()
                .id(3L)
                .name("pub")
                .avgRate(4.0f)
                .owner(author)
                .build();

        Review review = Review.builder()
                .id(reviewId)
                .user(author)
                .bar(bar)
                .rate(5)
                .content("great bar")
                .build();

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(userRepository.findById(nonAuthor.getId())).thenReturn(Optional.of(nonAuthor));

        assertThrows(RuntimeException.class ,() -> reviewService.deleteReviewById(reviewId, nonAuthor.getId()));
        verify(reviewRepository, never()).delete(any());

    }

    @Test
    void shouldThrowReviewNotFound(){
        Long reviewId = 10L;
        Long userId = 1L;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(
                RuntimeException.class, () -> reviewService.updateReview(reviewId, userId,
                        new ReviewUpdateRequestDTO(5, "great bar"))
        );
        assertEquals("review not found", ex.getMessage());

    }

}


