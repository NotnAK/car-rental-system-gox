package com.gox.domain.service;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.User;
import com.gox.domain.entity.user.UserRole;
import com.gox.domain.entity.wishlist.Wishlist;
import com.gox.domain.exception.UserException;
import com.gox.domain.repository.CarRepository;
import com.gox.domain.repository.UserRepository;
import com.gox.domain.repository.WishlistRepository;

import java.util.List;

public class UserService implements UserFacade{
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final CarRepository carRepository;

    public UserService(UserRepository userRepository,
                       WishlistRepository wishlistRepository,
                       CarRepository carRepository) {
        this.userRepository = userRepository;
        this.wishlistRepository = wishlistRepository;
        this.carRepository = carRepository;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.readByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        validateUser(user);
/*        if(user.getRole() != UserRole.ADMIN){
            Wishlist wishlist = new Wishlist();
            user.setWishlist(wishlist);
        }*/
        Wishlist wishlist = new Wishlist();
        user.setWishlist(wishlist);
        User createdUser = userRepository.create(user);
        return createdUser;
    }
    private void validateUser(User user) {
        if (user == null) {
            throw new UserException("User object must not be null.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserException("User email must not be empty.");
        }

        if (userRepository.readByEmail(user.getEmail()) != null) {
            throw new UserException("User with email '" + user.getEmail() + "' already exists.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserException("User name must not be empty.");
        }

        if (user.getRole() == null) {
            throw new UserException("User role must be specified.");
        }
    }
}
