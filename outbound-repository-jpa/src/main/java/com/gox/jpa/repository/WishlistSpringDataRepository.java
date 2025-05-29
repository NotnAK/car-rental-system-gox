package com.gox.jpa.repository;

import com.gox.domain.entity.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface WishlistSpringDataRepository extends JpaRepository<Wishlist, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "DELETE FROM wishlist_cars WHERE car_id = :carId",
            nativeQuery = true
    )
    void deleteCarFromAllWishlists(@Param("carId") Long carId);
}
