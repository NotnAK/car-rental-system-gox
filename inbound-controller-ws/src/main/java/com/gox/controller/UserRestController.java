package com.gox.controller;

import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.ReviewFacade;
import com.gox.domain.service.ReviewFactory;
import com.gox.domain.service.UserFacade;
import com.gox.domain.service.WishlistFacade;
import com.gox.mapper.ReviewMapper;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.UsersApi;
import com.gox.rest.dto.ReviewDto;
import com.gox.rest.dto.ReviewUpdateRequestDto;
import com.gox.rest.dto.UserDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class UserRestController implements UsersApi {
    private final ReviewFacade reviewFacade;
    private final ReviewMapper reviewMapper;
    private final WishlistFacade wishlistFacade;
    private final CurrentUserDetailService currentUserDetailService;
    private final ReviewFactory reviewFactory;
    private final UserMapper userMapper;
    private final UserFacade userFacade;

    public UserRestController(ReviewFacade reviewFacade,
                              ReviewMapper reviewMapper,
                              WishlistFacade wishlistFacade,
                              CurrentUserDetailService currentUserDetailService,
                              ReviewFactory reviewFactory,
                              UserMapper userMapper,
                              UserFacade userFacade) {
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
        this.wishlistFacade = wishlistFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.reviewFactory = reviewFactory;
        this.userMapper = userMapper;
        this.userFacade = userFacade;
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

    @Override
    public ResponseEntity<UserDto> getProfile() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        UserDto dto = userMapper.toDto(currentUser);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userFacade.getAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
}
