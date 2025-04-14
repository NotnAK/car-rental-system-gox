package com.gox.jpa.converter;

import com.gox.domain.entity.user.UserRole;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return UserRole.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid value for UserRole: " + dbData, e);
        }
    }
}
