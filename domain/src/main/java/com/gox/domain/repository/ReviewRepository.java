package com.gox.domain.repository;

import com.gox.domain.entity.review.Review;

import java.util.List;

public interface ReviewRepository {
    Review create(Review review);
    Review read(Long id);
    List<Review> findByUserId(Long userId);
    List<Review> findByCarId(Long carId);
    List<Review> findByRating(Integer rating);
    Review update(Review user);
    List<Review> findAll();
    void delete(Long id);
    void deleteByUserId(Long userId);
    void deleteByCarId(Long carId);
}
