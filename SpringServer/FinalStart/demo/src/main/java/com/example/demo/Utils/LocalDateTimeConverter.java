package com.example.demo.Utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
//Unused but left for reference
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}