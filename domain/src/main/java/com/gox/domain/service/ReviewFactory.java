package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.exception.ReviewException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.ReviewRepository;

import java.time.LocalDateTime;

public class ReviewFactory {
    private final CarRepository carRepository;
    private final ReviewRepository reviewRepository;

    public ReviewFactory(CarRepository carRepository, ReviewRepository reviewRepository) {
        this.carRepository = carRepository;
        this.reviewRepository = reviewRepository;
    }

    public Review createReview(Long carId, User user, int rating, String comment) throws ReviewException {
        // Проверка и загрузка автомобиля
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new ReviewException("Car not found with id: " + carId);
        }
        // Проверка пользователя
        if (user == null) {
            throw new ReviewException("User must not be null");
        }
        // Валидация рейтинга
        if (rating < 1 || rating > 5) {
            throw new ReviewException("Rating must be between 1 and 5");
        }
        // Проверка комментария
        if (comment == null || comment.trim().isEmpty()) {
            throw new ReviewException("Review comment must not be blank");
        }
        // Создаем новый Review и устанавливаем поля
        Review review = new Review();
        review.setCar(car);
        review.setUser(user);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());
        // При необходимости можно вызвать repository.create(review) для сохранения
        // Например:
        // return reviewRepository.create(review);
        return reviewRepository.create(review);
    }
}
