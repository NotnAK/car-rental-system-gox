package com.gox.domain.service;

import com.gox.domain.entity.review.Review;
import com.gox.domain.exception.ReviewException;
import com.gox.domain.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewService implements ReviewFacade {
    private final ReviewRepository repository;

    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }


    @Override
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
    }


    @Override
    public Review get(Long id) {
        if (id == null || id <= 0) {
            throw new ReviewException("Invalid review id: " + id);
        }
        Review review = repository.read(id);
        if (review == null) {
            throw new ReviewException("Review not found with id: " + id);
        }
        return review;
    }

    @Override
    public List<Review> getByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ReviewException("Invalid user id: " + userId);
        }
        return repository.findByUserId(userId);
    }

    @Override
    public List<Review> getByCarId(Long carId) {
        if (carId == null || carId <= 0) {
            throw new ReviewException("Invalid car id: " + carId);
        }
        return repository.findByCarId(carId);

    }

    @Override
    public List<Review> getByRating(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new ReviewException("Invalid rating: " + rating);
        }
        List<Review> reviews = repository.findByRating(rating);
        if (reviews == null || reviews.isEmpty()) {
            throw new ReviewException("No reviews found with rating: " + rating);
        }
        return reviews;
    }

    @Override
    public Review update(Review review) {
        if (review == null) {
            throw new ReviewException("Review must not be null");
        }
        if (review.getId() == null || review.getId() <= 0) {
            throw new ReviewException("Invalid review id for update: " + review.getId());
        }
        Review existing = repository.read(review.getId());
        if (existing == null) {
            throw new ReviewException("Review not found with id: " + review.getId());
        }
        // Проверяем новые данные
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new ReviewException("Rating must be between 1 and 5");
        }
        if (review.getComment() == null || review.getComment().isBlank()) {
            throw new ReviewException("Review comment must not be blank");
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
        if (reviews == null || reviews.isEmpty()) {
            throw new ReviewException("No reviews found");
        }
        return reviews;
    }
}
