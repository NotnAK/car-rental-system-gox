package com.gox.jpa.converter;


import com.gox.domain.entity.car.TransmissionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TransmissionTypeConverter implements AttributeConverter<TransmissionType, String> {
    @Override
    public String convertToDatabaseColumn(TransmissionType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public TransmissionType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try {
            return TransmissionType.valueOf(dbData);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Invalid value for TransmissionType: " + dbData, ex);
        }
    }
}