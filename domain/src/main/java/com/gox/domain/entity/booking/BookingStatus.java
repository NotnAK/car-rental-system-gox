package com.gox.domain.entity.booking;

public enum BookingStatus {
    PENDING,    // ожидает одобрения
    APPROVED,   // подтверждена админом
    CANCELLED,  // отменена до старта
    COMPLETED   // завершена
}