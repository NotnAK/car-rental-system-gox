package com.gox.controller;

import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.ReviewFacade;
import com.gox.domain.service.ReviewFactory;
import com.gox.domain.service.WishlistFacade;
import com.gox.mapper.ReviewMapper;
import com.gox.rest.api.CustomerApi;
import com.gox.rest.dto.ReviewCreateRequestDto;
import com.gox.rest.dto.ReviewDto;
import com.gox.rest.dto.ReviewUpdateRequestDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class CustomerRestController implements CustomerApi {
    private final ReviewFacade reviewFacade;
    private final ReviewMapper reviewMapper;
    private final WishlistFacade wishlistFacade;
    private final CurrentUserDetailService currentUserDetailService;
    private final ReviewFactory reviewFactory;

    public CustomerRestController(ReviewFacade reviewFacade,
                                  ReviewMapper reviewMapper,
                                  WishlistFacade wishlistFacade,
                                  CurrentUserDetailService currentUserDetailService,
                                  ReviewFactory reviewFactory) {
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
        this.wishlistFacade = wishlistFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.reviewFactory = reviewFactory;
    }
    @Override
    public ResponseEntity<String> addCarToWishlist(Long carId) {
        User user = currentUserDetailService.getFullCurrentUser();
        wishlistFacade.addCarToWishlist(user.getId(), carId);
        return ResponseEntity.ok("Car added successfully to wishlist.");
    }

    @Override
    public ResponseEntity<String> removeCarFromWishlist(Long carId) {
        User user = currentUserDetailService.getFullCurrentUser();
        wishlistFacade.removeCarFromWishlist(user.getId(), carId);
        return ResponseEntity.ok("Car removed successfully from wishlist.");
    }

    // POST /customer/reviews
    @Override
    public ResponseEntity<String> createReview(ReviewCreateRequestDto reviewCreateRequestDto) {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        reviewFactory.createReview(
                reviewCreateRequestDto.getCarId(),
                currentUser,
                reviewCreateRequestDto.getRating(),
                reviewCreateRequestDto.getComment()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // DELETE /customer/reviews/{reviewId} – удаление отзыва текущего пользователя
    @Override
    public ResponseEntity<Void> deleteReview(Integer reviewId) {
        Review existing = reviewFacade.get(reviewId.longValue());
        User currentUser = currentUserDetailService.getFullCurrentUser();

        if (!existing.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        reviewFacade.deleteReview(reviewId.longValue());
        return ResponseEntity.ok().build();
    }
    // GET /customer/reviews
    @Override
    public ResponseEntity<List<ReviewDto>> getOwnReviews() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        List<Review> reviews = reviewFacade.getByUserId(currentUser.getId());
        List<ReviewDto> dtos = reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    // PUT /customer/reviews/{reviewId} – обновление отзыва текущего пользователя
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
