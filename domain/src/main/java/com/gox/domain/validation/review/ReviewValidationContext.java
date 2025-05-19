package com.gox.domain.validation.review;


import com.gox.domain.entity.user.User;

public class ReviewValidationContext {
    private final Long   carId;
    private final Long   reviewId;
    private final User   user;
    private final Integer rating;
    private final String comment;

    private ReviewValidationContext(Builder builder) {
        this.carId    = builder.carId;
        this.reviewId = builder.reviewId;
        this.user     = builder.user;
        this.rating   = builder.rating;
        this.comment  = builder.comment;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getCarId() {
        return carId;
    }

    public Long getReviewId() {
        return reviewId;
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

    public static class Builder {
        private Long   carId;
        private Long   reviewId;
        private User   user;
        private Integer rating;
        private String comment;

        public Builder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public Builder reviewId(Long reviewId) {
            this.reviewId = reviewId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public ReviewValidationContext build() {
            return new ReviewValidationContext(this);
        }
    }
}