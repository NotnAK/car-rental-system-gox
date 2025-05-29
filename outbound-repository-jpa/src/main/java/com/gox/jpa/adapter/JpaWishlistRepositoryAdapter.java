package com.gox.jpa.adapter;

import com.gox.domain.entity.wishlist.Wishlist;
import com.gox.domain.repository.WishlistRepository;
import com.gox.jpa.repository.WishlistSpringDataRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaWishlistRepositoryAdapter implements WishlistRepository {
    private final WishlistSpringDataRepository wishlistSpringDataRepository;

    public JpaWishlistRepositoryAdapter(WishlistSpringDataRepository wishlistSpringDataRepository) {
        this.wishlistSpringDataRepository = wishlistSpringDataRepository;
    }
    @Override
    public Wishlist read(Long id) {
        return wishlistSpringDataRepository.findById(id).orElse(null);
    }

    @Override
    public Wishlist create(Wishlist wishlist) {
        return wishlistSpringDataRepository.save(wishlist);
    }

    @Override
    public Wishlist update(Wishlist wishlist) {
        return wishlistSpringDataRepository.save(wishlist);
    }

    @Override
    public void delete(Long id) {
        wishlistSpringDataRepository.deleteById(id);
    }

    @Override
    public void deleteCarFromAllWishlists(Long carId) {
        wishlistSpringDataRepository.deleteCarFromAllWishlists(carId);
    }
}
