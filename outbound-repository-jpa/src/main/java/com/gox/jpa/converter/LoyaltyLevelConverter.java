package com.gox.jpa.converter;

import com.gox.domain.entity.LoyaltyLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LoyaltyLevelConverter implements AttributeConverter<LoyaltyLevel, String> {

    @Override
    public String convertToDatabaseColumn(LoyaltyLevel attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public LoyaltyLevel convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return LoyaltyLevel.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid value for LoyaltyLevel: " + dbData, e);
        }
    }
}
