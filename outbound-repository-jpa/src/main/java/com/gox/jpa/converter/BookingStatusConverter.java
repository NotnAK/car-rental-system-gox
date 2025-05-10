package com.gox.jpa.converter;

import com.gox.domain.entity.booking.BookingStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BookingStatusConverter implements AttributeConverter<BookingStatus, String> {

    @Override
    public String convertToDatabaseColumn(BookingStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public BookingStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return BookingStatus.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Unknown BookingStatus: " + dbData, ex);
        }
    }
}