package com.gox.domain.service;

public interface WishlistFacade {
    void addCarToWishlist(Long userId, Long carId);
    void removeCarFromWishlist(Long userId, Long carId);
}
