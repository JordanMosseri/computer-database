package com.excilys.computerdatabase.modele;

import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDateTime, java.sql.Date> {

  @Override
  public java.sql.Date convertToDatabaseColumn(LocalDateTime entityValue) {
    if (entityValue != null) {
      return java.sql.Date.valueOf(entityValue.toLocalDate());
    }
    return null;
  }

  @Override
  public LocalDateTime convertToEntityAttribute(java.sql.Date databaseValue) {
    if (databaseValue != null) {
      return databaseValue.toLocalDate().atTime(0, 0);
    }
    return null;
  }
}