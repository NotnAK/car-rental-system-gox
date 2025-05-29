package com.gox.domain.service.booking;

import com.gox.domain.entity.car.Car;
import com.gox.domain.entity.user.LoyaltyLevel;
import com.gox.domain.entity.user.User;
import com.gox.domain.vo.BookingEstimate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;

public class BookingEstimateCalculator {
    private static final BigDecimal URGENT_TRANSFER_FEE = new BigDecimal("30");

    public BookingEstimate calculate(
            Car car,
            User user,
            OffsetDateTime start,
            OffsetDateTime end,
            Long pickupLocationId,
            Long dropoffLocationId
    ) {
        OffsetDateTime now = OffsetDateTime.now();
        long hoursUntilStart = Duration.between(now, start).toHours();
        boolean urgent = hoursUntilStart < 10;
        BigDecimal transferFee = BigDecimal.ZERO;
        if (!pickupLocationId.equals(dropoffLocationId) && urgent) {
            transferFee = URGENT_TRANSFER_FEE;
        }
        long seconds = Duration.between(start, end).getSeconds();
        long days = (seconds + 86_400 - 1) / 86_400;
        BigDecimal basePrice = car.getPricePerDay().multiply(BigDecimal.valueOf(days));
        LoyaltyLevel loyaltyLevel = (user == null)?LoyaltyLevel.STANDARD:user.getLoyaltyLevel();
        double loyaltyRate = switch (loyaltyLevel) {
            case SILVER -> 0.05;
            case GOLD   -> 0.10;
            default     -> 0.0;
        };
        BigDecimal loyaltyDiscount = basePrice.multiply(BigDecimal.valueOf(loyaltyRate));
        BigDecimal discountedPrice = basePrice.subtract(loyaltyDiscount);
        BigDecimal totalPrice = discountedPrice.add(transferFee);
        return new BookingEstimate(
                (int) days,
                basePrice,
                loyaltyDiscount,
                discountedPrice,
                urgent,
                transferFee,
                totalPrice
        );
    }
}
