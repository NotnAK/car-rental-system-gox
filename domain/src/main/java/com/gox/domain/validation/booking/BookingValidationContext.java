package com.gox.domain.validation.booking;

import com.gox.domain.entity.user.User;

import java.time.OffsetDateTime;

public class BookingValidationContext {
    private final Long carId;
    private final Long pickupLocationId;
    private final Long dropoffLocationId;
    private final User user;
    private final OffsetDateTime start;
    private final OffsetDateTime end;

    public BookingValidationContext(Long carId,
                                    Long pickupLocationId,
                                    Long dropoffLocationId,
                                    User user,
                                    OffsetDateTime start,
                                    OffsetDateTime end) {
        this.carId             = carId;
        this.pickupLocationId  = pickupLocationId;
        this.dropoffLocationId = dropoffLocationId;
        this.user              = user;
        this.start             = start;
        this.end               = end;
    }

    public Long getCarId()             { return carId; }
    public Long getPickupLocationId()  { return pickupLocationId; }
    public Long getDropoffLocationId() { return dropoffLocationId; }
    public User getUser()              { return user; }
    public OffsetDateTime getStart()   { return start; }
    public OffsetDateTime getEnd()     { return end; }
}