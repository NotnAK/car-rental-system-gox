package com.gox.domain.service.booking;


import com.gox.domain.entity.booking.Booking;
import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.user.LoyaltyLevel;
import com.gox.domain.entity.user.User;
import com.gox.domain.repository.BookingRepository;
import com.gox.domain.repository.UserRepository;

import java.util.List;

public class BookingLoyaltyUpdater {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingLoyaltyUpdater(
            BookingRepository bookingRepository,
            UserRepository userRepository
    ) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public void updateLoyalty(User user) {
        List<Booking> completed = bookingRepository.findByUserIdAndStatus(user.getId(), BookingStatus.COMPLETED);

        if (completed.size() >= 10 && user.getLoyaltyLevel() == LoyaltyLevel.SILVER) {
            user.setLoyaltyLevel(LoyaltyLevel.GOLD);
            userRepository.update(user);

        } else if (completed.size() >= 5 && user.getLoyaltyLevel() == LoyaltyLevel.STANDARD) {
            user.setLoyaltyLevel(LoyaltyLevel.SILVER);
            userRepository.update(user);
        }
    }
}
