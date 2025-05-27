package com.gox.controller;

import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.photo.Photo;
import com.gox.domain.entity.review.Review;
import com.gox.domain.entity.user.User;
import com.gox.domain.service.*;
import com.gox.mapper.BookingMapper;
import com.gox.mapper.PhotoMapper;
import com.gox.mapper.ReviewMapper;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.UsersApi;
import com.gox.rest.dto.*;
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
    private final UserMapper userMapper;
    private final UserFacade userFacade;
    private final PhotoFacade photoFacade;
    private final PhotoMapper photoMapper;
    private final BookingMapper bookingMapper;
    private final BookingFacade bookingFacade;
    public UserRestController(ReviewFacade reviewFacade,
                              ReviewMapper reviewMapper,
                              WishlistFacade wishlistFacade,
                              CurrentUserDetailService currentUserDetailService,
                              UserMapper userMapper,
                              UserFacade userFacade,
                              PhotoFacade photoFacade,
                              PhotoMapper photoMapper,
                              BookingMapper bookingMapper,
                              BookingFacade bookingFacade) {
        this.reviewFacade = reviewFacade;
        this.reviewMapper = reviewMapper;
        this.wishlistFacade = wishlistFacade;
        this.currentUserDetailService = currentUserDetailService;
        this.userMapper = userMapper;
        this.userFacade = userFacade;
        this.photoFacade = photoFacade;      // ← сохраняем
        this.photoMapper = photoMapper;      // ← сохраняем
        this.bookingMapper = bookingMapper;
        this.bookingFacade = bookingFacade;

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
        // 1) Получаем сущность пользователя
        User currentUser = currentUserDetailService.getFullCurrentUser();
        // 2) Мапим её в DTO (в том числе должен быть заполнен wishlist, если настроен WishlistMapper)
        UserDto dto = userMapper.toDto(currentUser);

        // 3) Если у пользователя есть wishlist, заполняем превью для каждой машины
        if (dto.getWishlist() != null && dto.getWishlist().getCars() != null) {
            List<CarDto> carsWithPreview = dto.getWishlist().getCars().stream()
                    .map(carDto -> {
                        // получаем entity-превью по carId
                        Photo previewEntity = photoFacade.getPreviewForCar(carDto.getId());
                        PhotoDto previewDto = photoMapper.toDto(previewEntity);
                        // ставим в CarDto
                        carDto.setPreview(previewDto);
                        return carDto;
                    })
                    .collect(Collectors.toList());
            dto.getWishlist().setCars(carsWithPreview);
        }

        // 4) Возвращаем результат
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<List<UserSummaryDto>> getUsers() {
        List<User> users = userFacade.getAllUsers();
        List<UserSummaryDto> userDtos = users.stream()
                .map(userMapper::toSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
    @Override
    public ResponseEntity<List<Long>> getWishlistCarIds() {
        Long userId = currentUserDetailService.getFullCurrentUser().getId();
        List<Long> ids = userFacade.getWishlistCarIds(userId);
        return ResponseEntity.ok(ids);
    }
    @Override
    public ResponseEntity<UserDto> updateProfile(UserUpdateRequestDto dto) {
        User current = currentUserDetailService.getFullCurrentUser();
        User updated = userFacade.updateProfile(
                current,
                dto.getName(),
                dto.getPhone(),
                dto.getAddress()
        );
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
    @Override
    public ResponseEntity<List<BookingSummaryDto>> getOwnBookings() {
        User currentUser = currentUserDetailService.getFullCurrentUser();
        List<Booking> bookings = bookingFacade.getByUserId(currentUser.getId());
        List<BookingSummaryDto> dtos = bookings.stream()
                .map(bookingMapper::toSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
