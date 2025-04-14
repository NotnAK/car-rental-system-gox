package com.gox.domain.repository;

import com.gox.domain.entity.wishlist.Wishlist;

public interface WishlistRepository {
    Wishlist read(Long id);
    Wishlist create(Wishlist wishlist);
    Wishlist update(Wishlist wishlist);
    void delete(Long id);
}
