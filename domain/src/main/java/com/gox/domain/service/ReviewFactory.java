package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.exception.CarNotFoundException;
import com.gox.domain.exception.ReviewValidationException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.ReviewRepository;
import com.gox.domain.validation.ValidationExecutor;
import com.gox.domain.validation.api.ValidationRule;
import com.gox.domain.validation.review.ReviewValidationContext;
import com.gox.domain.validation.review.rules.*;

import java.time.OffsetDateTime;
import java.util.List;

public class ReviewFactory {
    private final CarRepository carRepository;
    private final ReviewRepository reviewRepository;
    private final List<ValidationRule<ReviewValidationContext>> createRules;

    public ReviewFactory(CarRepository carRepository, ReviewRepository reviewRepository) {
        this.carRepository = carRepository;
        this.reviewRepository = reviewRepository;
        this.createRules = List.of(
                new ReviewCarIdNotNullRule(),
                new ReviewUserNotNullRule(),
                new ReviewRatingRangeRule(),
                new ReviewCommentNotEmptyRule(),
                new ReviewCommentLengthRule()
        );
    }

    public Review create(Long carId, User user, Integer rating, String comment){
        var ctx = ReviewValidationContext.builder()
                .carId(carId)
                .user(user)
                .rating(rating)
                .comment(comment)
                .build();
        ValidationExecutor.validateOrThrow(
                ctx,
                createRules,
                ReviewValidationException::new
        );
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        Review review = new Review();
        review.setCar(car);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(OffsetDateTime.now());
        return reviewRepository.create(review);
    }
}
