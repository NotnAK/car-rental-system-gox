package com.gox.domain.service;

import com.gox.domain.entity.review.Review;
import com.gox.domain.exception.ReviewNotFoundException;
import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.repository.ReviewRepository;
import com.gox.domain.validation.api.ValidationResult;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;
import com.gox.domain.validation.review.rules.*;

import java.util.List;

public class ReviewService implements ReviewFacade {
    private final ReviewRepository repository;
    private final List<ValidationRule<ReviewValidationContext>> updateRules;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
        this.updateRules = List.of(
                new ReviewIdNotNullRule(),
                new ReviewCarIdNotNullRule(),
                new ReviewUserNotNullRule(),
                new ReviewRatingRangeRule(),
                new ReviewCommentNotEmptyRule(),
                new ReviewCommentMaxLengthRule()
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
        Review review = repository.read(id);
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
        return repository.findByUserId(userId);
    }

    @Override
    public List<Review> getByCarId(Long carId) {
        if (carId == null || carId <= 0) {
            throw new ReviewValidationException("Invalid car id: " + carId);
        }
        return repository.findByCarId(carId);

    }

    @Override
    public List<Review> getByRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ReviewValidationException("Invalid rating: " + rating);
        }
        return repository.findByRating(rating);
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
        Review existing = repository.read(review.getId());
        if (existing == null) {
            throw new ReviewNotFoundException("Review not found with id: " + review.getId());
        }
        // Обновляем только изменяемые поля (rating и comment)
        existing.setRating(review.getRating());
        existing.setComment(review.getComment());
        // createdAt обычно не меняется
        return repository.update(existing);
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> reviews = repository.findAll();
        return reviews;
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new ReviewValidationException("Invalid review id: " + reviewId);
        }
        Review existing = repository.read(reviewId);
        if (existing == null) {
            throw new ReviewNotFoundException("Review not found with id: " + reviewId);
        }
        repository.delete(reviewId);
    }
}
