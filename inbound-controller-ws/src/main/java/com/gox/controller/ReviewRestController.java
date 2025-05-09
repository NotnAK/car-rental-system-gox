package com.gox.controller;

import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.entity.user.UserRole;
import com.gox.domain.service.CarFacade;
import com.gox.domain.service.ReviewFacade;
import com.gox.mapper.CarMapper;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.ReviewsApi;
import com.gox.rest.dto.ReviewUpdateRequestDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewRestController implements ReviewsApi {
    private final ReviewFacade reviewFacade;
    private final CurrentUserDetailService currentUserDetailService;

    public ReviewRestController(CurrentUserDetailService currentUserDetailService,
                                ReviewFacade reviewFacade) {
        this.reviewFacade = reviewFacade;
        this.currentUserDetailService = currentUserDetailService;
    }



    @Override
    public ResponseEntity<Void> deleteReview(Integer reviewId) {
        // 1) Получаем существующий отзыв
        Review existing = reviewFacade.get(reviewId.longValue());
        // 2) Берём текущего пользователя из контекста
        User currentUser = currentUserDetailService.getFullCurrentUser();

        // 3) Если роль не ADMIN, проверяем, что отзыв принадлежит текущему пользователю
        if (!currentUser.getRole().equals(UserRole.ADMIN)) {
            if (!existing.getUser().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        // 4) Удаляем отзыв
        reviewFacade.deleteReview(reviewId.longValue());
        return ResponseEntity.ok().build();
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
