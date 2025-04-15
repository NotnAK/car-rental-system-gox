package com.gox.jpa.repository;

import com.gox.domain.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewSpringDataRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByCarId(Long carId);
    List<Review> findByRating(Integer rating);
}
