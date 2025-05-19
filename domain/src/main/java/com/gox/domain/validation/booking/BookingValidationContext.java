package com.gox.domain.validation.booking;

import com.gox.domain.entity.booking.BookingStatus;
import com.gox.domain.entity.user.User;

import java.time.OffsetDateTime;

public class BookingValidationContext {
    private final Long carId;
    private final Long pickupLocationId;
    private final Long dropoffLocationId;
    private final User user;
    private final OffsetDateTime start;
    private final OffsetDateTime end;
    private final OffsetDateTime actualReturnDate;  // ← новое поле
    private final BookingStatus status;

    private BookingValidationContext(Builder builder) {
        this.carId = builder.carId;
        this.pickupLocationId = builder.pickupLocationId;
        this.dropoffLocationId = builder.dropoffLocationId;
        this.user = builder.user;
        this.start = builder.start;
        this.end = builder.end;
        this.actualReturnDate = builder.actualReturnDate;
        this.status = builder.status;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long carId;
        private Long pickupLocationId;
        private Long dropoffLocationId;
        private User user;
        private OffsetDateTime start;
        private OffsetDateTime end;
        private OffsetDateTime actualReturnDate;  // ← поле для билда
        private BookingStatus status;
        public Builder carId(Long carId) {
            this.carId = carId;
            return this;
        }

        public Builder pickupLocationId(Long pickupLocationId) {
            this.pickupLocationId = pickupLocationId;
            return this;
        }

        public Builder dropoffLocationId(Long dropoffLocationId) {
            this.dropoffLocationId = dropoffLocationId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder start(OffsetDateTime start) {
            this.start = start;
            return this;
        }

        public Builder end(OffsetDateTime end) {
            this.end = end;
            return this;
        }

        public Builder actualReturnDate(OffsetDateTime actualReturnDate) {
            this.actualReturnDate = actualReturnDate;
            return this;
        }
        public Builder status(BookingStatus status) {
            this.status = status;
            return this;
        }
        public BookingValidationContext build() {
            return new BookingValidationContext(this);
        }
    }

    public Long getCarId() {
        return carId;
    }

    public Long getPickupLocationId() {
        return pickupLocationId;
    }

    public Long getDropoffLocationId() {
        return dropoffLocationId;
    }

    public User getUser() {
        return user;
    }

    public OffsetDateTime getStart() {
        return start;
    }

    public OffsetDateTime getEnd() {
        return end;
    }

    public OffsetDateTime getActualReturnDate() {
        return actualReturnDate;
    }

    public BookingStatus getStatus() {
        return status;
    }
}
