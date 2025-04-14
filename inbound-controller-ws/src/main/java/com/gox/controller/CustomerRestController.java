package com.gox.controller;

import com.gox.domain.entity.user.User;
import com.gox.domain.exception.CarException;
import com.gox.domain.exception.UserException;
import com.gox.domain.exception.WishlistException;
import com.gox.domain.service.UserFacade;
import com.gox.domain.service.WishlistFacade;
import com.gox.mapper.UserMapper;
import com.gox.rest.api.CustomerApi;
import com.gox.rest.dto.UserDto;
import com.gox.security.CurrentUserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
public class CustomerRestController implements CustomerApi {
    private final WishlistFacade wishlistFacade;
    private final CurrentUserDetailService currentUserDetailService;

    public CustomerRestController(WishlistFacade wishlistFacade,
                                  CurrentUserDetailService currentUserDetailService) {
        this.wishlistFacade = wishlistFacade;
        this.currentUserDetailService = currentUserDetailService;
    }
    @Override
    public ResponseEntity<String> addCarToWishlist(Long carId) {
        try {
            User user = currentUserDetailService.getFullCurrentUser();
            wishlistFacade.addCarToWishlist(user.getId(), carId);
            return ResponseEntity.ok("Car added successfully to wishlist.");
        } catch (UserException | CarException ex) {
            // Если не найден пользователь или автомобиль – возвращаем 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (WishlistException ex) {
            // Если логическая ошибка, например, машина уже добавлена – возвращаем 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            // Неожиданные исключения – возвращаем 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal error: " + ex.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> removeCarFromWishlist(Long carId) {
        try {
            User user = currentUserDetailService.getFullCurrentUser();
            wishlistFacade.removeCarFromWishlist(user.getId(), carId);
            return ResponseEntity.ok("Car removed successfully from wishlist.");
        } catch (UserException | CarException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (WishlistException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal error: " + ex.getMessage());
        }
    }
}
