package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.entity.wishlist.Wishlist;
import com.gox.domain.exception.*;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.repository.WishlistRepository;

public class WishlistService implements WishlistFacade {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public WishlistService(WishlistRepository wishlistRepository,
                           UserRepository userRepository,
                           CarRepository carRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void addCarToWishlist(Long userId, Long carId) {
        User user = userRepository.read(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        Wishlist wishlist = user.getWishlist();
        if (wishlist == null) {
            throw new WishlistNotFoundException("Wishlist not found for user id: " + userId);
        }
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        boolean alreadyInWishlist = wishlist.getCars().stream()
                .anyMatch(c -> c.getId().equals(car.getId()));
        if (alreadyInWishlist) {
            throw new WishlistValidationException("Car (id=" + carId + ") is already in user's wishlist");
        }
        wishlist.addCar(car);
        wishlistRepository.update(wishlist);
    }

    @Override
    public void removeCarFromWishlist(Long userId, Long carId) {
        User user = userRepository.read(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        Wishlist wishlist = user.getWishlist();
        if (wishlist == null) {
            throw new WishlistNotFoundException("Wishlist not found for user id: " + userId);
        }
        Car car = carRepository.read(carId);
        if (car == null) {
            throw new CarNotFoundException("Car not found with id: " + carId);
        }
        boolean isInWishlist = wishlist.getCars().stream()
                .anyMatch(c -> c.getId().equals(car.getId()));
        if (!isInWishlist) {
            throw new WishlistValidationException("Car (id=" + carId + ") is not in user's wishlist");
        }
        wishlist.removeCar(car);
        wishlistRepository.update(wishlist);
    }
}