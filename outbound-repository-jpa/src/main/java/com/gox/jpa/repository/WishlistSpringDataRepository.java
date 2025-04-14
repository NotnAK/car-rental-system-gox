package com.gox.jpa.repository;

import com.gox.domain.entity.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistSpringDataRepository extends JpaRepository<Wishlist, Long> {
}
