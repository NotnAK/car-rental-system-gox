package com.gox.jpa.converter;

import com.gox.domain.entity.CarState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CarStateConverter implements AttributeConverter<CarState, String> {

    @Override
    public String convertToDatabaseColumn(CarState attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.name();
    }

    @Override
    public CarState convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return CarState.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Invalid value for CarState: " + dbData, ex);
        }
    }
}
