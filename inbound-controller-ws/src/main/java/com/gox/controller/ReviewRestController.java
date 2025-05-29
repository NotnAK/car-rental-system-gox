package com.gox.controller;

import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.entity.user.UserRole;
import com.gox.domain.service.ReviewFacade;
import com.gox.mapper.ReviewMapper;
import com.gox.rest.api.ReviewsApi;
import com.gox.rest.dto.ReviewDto;
import com.gox.rest.dto.ReviewUpdateRequestDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewRestController implements ReviewsApi {
    private final ReviewFacade reviewFacade;
    private final CurrentUserDetailService currentUserDetailService;
    private final ReviewMapper reviewMapper;
    public ReviewRestController(CurrentUserDetailService currentUserDetailService,
                                ReviewFacade reviewFacade,
                                ReviewMapper reviewMapper) {
        this.reviewFacade = reviewFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.reviewMapper = reviewMapper;
    }



    @Override
    public ResponseEntity<Void> deleteReview(Integer reviewId) {
        Review existing = reviewFacade.get(reviewId.longValue());
        User currentUser = currentUserDetailService.getFullCurrentUser();
        if (!currentUser.getRole().equals(UserRole.ADMIN)) {
            if (!existing.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        reviewFacade.deleteReview(reviewId.longValue());
        return ResponseEntity.ok().build();
    }
    @Override
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> all = reviewFacade.getAll();
        List<ReviewDto> dtos = all.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @Override
    public ResponseEntity<String> updateReview(Integer reviewId, ReviewUpdateRequestDto dto) {
        Review existing = reviewFacade.get(reviewId.longValue());
        User currentUser = currentUserDetailService.getFullCurrentUser();

        if (!existing.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());
        reviewFacade.update(existing);
        return ResponseEntity.ok().build();
    }
}
