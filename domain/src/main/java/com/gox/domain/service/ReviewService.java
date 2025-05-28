package com.gox.domain.service;

import com.gox.domain.entity.review.Review;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.ReviewNotFoundException;
import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.exception.UserNotFoundException;
import com.gox.domain.repository.ReviewRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;
import com.gox.domain.validation.review.rules.*;

import java.time.OffsetDateTime;
import java.util.List;

public class ReviewService implements ReviewFacade {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final List<ValidationRule<ReviewValidationContext>> updateRules;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository   = userRepository;
        this.updateRules = List.of(
                new ReviewIdNotNullRule(),
                new ReviewCarIdNotNullRule(),
                new ReviewUserNotNullRule(),
                new ReviewRatingRangeRule(),
                new ReviewCommentNotEmptyRule(),
                new ReviewCommentLengthRule()
        );
    }


/*    @Override
    public Review create(Review review) {
        if (review == null) {
            throw new ReviewException("Review must not be null");
        }
        if (review.getUser() == null) {
            throw new ReviewException("Review must have an associated user");
        }
        if (review.getCar() == null) {
            throw new ReviewException("Review must have an associated car");
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ReviewException("Rating must be between 1 and 5");
        }
        if (review.getComment() == null || review.getComment().isBlank()) {
            throw new ReviewException("Review comment must not be blank");
        }
        if (review.getCreatedAt() == null) {
            review.setCreatedAt(LocalDateTime.now());
        }
        return repository.create(review);
    }*/


    @Override
    public Review get(Long id) {
        if (id == null || id <= 0) {
            throw new ReviewValidationException("Invalid review id: " + id);
        }
        Review review = reviewRepository.read(id);
        if (review == null) {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
        return review;
    }

    @Override
    public List<Review> getByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ReviewValidationException("Invalid user id: " + userId);
        }
        if (userRepository.read(userId) == null) {
            throw new CarNotFoundException("User with ID " + userId + " not found");
        }
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> getByCarId(Long carId) {
        if (carId == null || carId <= 0) {
            throw new ReviewValidationException("Invalid car id: " + carId);
        }
        return reviewRepository.findByCarId(carId);

    }

    @Override
    public List<Review> getByRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ReviewValidationException("Invalid rating: " + rating);
        }
        return reviewRepository.findByRating(rating);
/*        if (reviews == null || reviews.isEmpty()) {
            throw new ReviewNotFoundException("No reviews found with rating: " + rating);
        }*/
    }

    @Override
    public Review update(Review review) {
        var ctx = ReviewValidationContext.builder()
                .carId(review.getCar().getId())
                .reviewId(review.getId())
                .user(review.getUser())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
        var vr  = new ValidationResult();
        for (var rule : updateRules) {
            rule.validate(ctx, vr);
        }
        if (vr.hasErrors()) {
            throw new ReviewValidationException(vr.getCombinedMessage());
        }
        Review existing = reviewRepository.read(review.getId());
        if (existing == null) {
            throw new ReviewNotFoundException("Review not found with id: " + review.getId());
        }
        // Обновляем только изменяемые поля (rating и comment)
        existing.setRating(review.getRating());
        existing.setComment(review.getComment());
        existing.setUpdatedAt(OffsetDateTime.now());
        // createdAt обычно не меняется
        return reviewRepository.update(existing);
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new ReviewValidationException("Invalid review id: " + reviewId);
        }
        Review existing = reviewRepository.read(reviewId);
        if (existing == null) {
            throw new ReviewNotFoundException("Review not found with id: " + reviewId);
        }
        reviewRepository.delete(reviewId);
    }
    public void deleteByUserId(Long userId) {
        reviewRepository.deleteByUserId(userId);
    }
}
