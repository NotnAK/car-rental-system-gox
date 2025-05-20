package com.gox.jpa.converter;

import com.gox.domain.entity.car.FuelType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class FuelTypeConverter implements AttributeConverter<FuelType, String> {
    @Override
    public String convertToDatabaseColumn(FuelType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public FuelType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return FuelType.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Invalid value for FuelType: " + dbData, ex);
        }
    }
}