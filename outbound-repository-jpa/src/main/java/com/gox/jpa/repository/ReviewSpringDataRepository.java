package com.gox.jpa.repository;

import com.gox.domain.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewSpringDataRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByCarId(Long carId);
    List<Review> findByRating(Integer rating);
    List<Review> findAllByOrderByIdDesc();
    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
    @Modifying
    @Transactional
    void deleteByCarId(Long userId);

}
