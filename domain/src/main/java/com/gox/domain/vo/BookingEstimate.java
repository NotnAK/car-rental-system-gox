package com.gox.domain.vo;

import java.math.BigDecimal;

public class BookingEstimate {
    private final int days;
    private final BigDecimal basePrice;
    private final BigDecimal loyaltyDiscount;
    private final BigDecimal discountedPrice;
    private final boolean urgent;
    private final BigDecimal transferFee;
    private final BigDecimal totalPrice;

    public BookingEstimate(int days,
                           BigDecimal basePrice,
                           BigDecimal loyaltyDiscount,
                           BigDecimal discountedPrice,
                           boolean urgent,
                           BigDecimal transferFee,
                           BigDecimal totalPrice) {
        this.days = days;
        this.basePrice = basePrice;
        this.loyaltyDiscount = loyaltyDiscount;
        this.discountedPrice = discountedPrice;
        this.urgent = urgent;
        this.transferFee = transferFee;
        this.totalPrice = totalPrice;
    }
    public int getDays() { return days; }
    public BigDecimal getBasePrice() { return basePrice; }
    public BigDecimal getLoyaltyDiscount() { return loyaltyDiscount; }
    public BigDecimal getDiscountedPrice() { return discountedPrice; }
    public boolean isUrgent() { return urgent; }
    public BigDecimal getTransferFee() { return transferFee; }
    public BigDecimal getTotalPrice() { return totalPrice; }
}
