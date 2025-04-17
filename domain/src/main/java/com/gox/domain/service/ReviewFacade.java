package com.gox.domain.service;

import com.gox.domain.entity.review.Review;

import java.util.List;

public interface ReviewFacade {
/*    Review create(Review review);*/
    Review get(Long id);
    List<Review> getByUserId(Long userId);
    List<Review> getByCarId(Long carId);
    List<Review> getByRating(Integer rating);
    Review update(Review user);
    List<Review> getAllReviews();
    void deleteReview(Long reviewId);
}
