package com.wanted.bobo.expense.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, String> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public String convertToDatabaseColumn(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) : null;
    }

    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        return dbData != null ? LocalDate.parse(dbData, DateTimeFormatter.ofPattern(DATE_FORMAT)) : null;
    }
}
