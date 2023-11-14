package com.wanted.bobo.budget.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    private static final String DATE_FORMAT = "yyyy-MM";

    @Override
    public String convertToDatabaseColumn(YearMonth yearMonth) {
        return yearMonth != null ? yearMonth.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) : null;
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return dbData != null ? YearMonth.parse(dbData, DateTimeFormatter.ofPattern(DATE_FORMAT)) : null;
    }
}
