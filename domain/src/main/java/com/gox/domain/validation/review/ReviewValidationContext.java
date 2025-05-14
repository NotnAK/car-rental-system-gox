package com.gox.domain.validation.review;


import com.gox.domain.entity.user.User;

public class ReviewValidationContext {
    private final Long carId;
    private final Long reviewId;
    private final User user;
    private final Integer rating;
    private final String comment;

    public ReviewValidationContext(Long carId, Long reviewId, User user, Integer rating, String comment) {
        this.carId = carId;
        this.reviewId = reviewId;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }
    public Long getCarId() {
        return carId;
    }

    public User getUser() {
        return user;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Long getReviewId() {
        return reviewId;
    }
}