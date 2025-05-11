package com.gox.domain.vo;

import java.time.OffsetDateTime;

public class BookingInterval {
    private final OffsetDateTime start;
    private final OffsetDateTime end;

    public BookingInterval(OffsetDateTime start, OffsetDateTime end) {
        this.start = start;
        this.end   = end;
    }

    public OffsetDateTime getStart() { return start; }
    public OffsetDateTime getEnd()   { return end; }
}