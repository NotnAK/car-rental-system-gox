package com.gox.jpa.converter;

import com.gox.domain.entity.car.CarCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class CarCategoryConverter implements AttributeConverter<CarCategory, String> {
    @Override
    public String convertToDatabaseColumn(CarCategory attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CarCategory convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return CarCategory.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Invalid value for CarCategory: " + dbData, ex);
        }
    }
}
