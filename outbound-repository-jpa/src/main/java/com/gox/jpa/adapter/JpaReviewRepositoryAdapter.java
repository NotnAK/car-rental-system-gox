package com.gox.jpa.adapter;

import com.gox.domain.entity.review.Review;
import com.gox.domain.repository.ReviewRepository;
import com.gox.jpa.repository.ReviewSpringDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaReviewRepositoryAdapter implements ReviewRepository {
    private final ReviewSpringDataRepository reviewSpringDataRepository;

    public JpaReviewRepositoryAdapter(ReviewSpringDataRepository reviewSpringDataRepository) {
        this.reviewSpringDataRepository = reviewSpringDataRepository;
    }

    @Override
    public Review create(Review review) {
        return reviewSpringDataRepository.save(review);
    }

    @Override
    public Review read(Long id) {
        return reviewSpringDataRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> findByUserId(Long userId) {
        return reviewSpringDataRepository.findByUserId(userId);
    }

    @Override
    public List<Review> findByCarId(Long carId) {
        return reviewSpringDataRepository.findByCarId(carId);
    }

    @Override
    public List<Review> findByRating(Integer rating) {
        return reviewSpringDataRepository.findByRating(rating);
    }

    @Override
    public Review update(Review review) {
        return reviewSpringDataRepository.save(review);
    }

    @Override
    public List<Review> findAll() {
        return reviewSpringDataRepository.findAllByOrderByIdDesc();
    }
    @Override
    public void delete(Long id) {
        reviewSpringDataRepository.deleteById(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        reviewSpringDataRepository.deleteByUserId(userId);
    }
}
