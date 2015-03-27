package com.excilys.computerdatabase.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.i18n.LocaleContextHolder;

public class Utils {
	
	private static final String FORMAT_DATE_EN = "yyyy-MM-dd";
	private static final String FORMAT_DATE_FR = "dd-MM-yyyy";
	private static final String FORMAT_DATE_ES = "dd/MM/yyyy";
	
	public static final String REGEX_DATE_EN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	public static final String REGEX_DATE_FR = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
	public static final String REGEX_DATE_ES = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	
	public static final String REGEX_INTEGER = "[+-]?[0-9]++";
	
	/**
	 * Checks if a string matches a given regex pattern.
	 * @param stringPattern
	 * @param stringToCheck
	 * @return
	 */
	public static boolean checkString(String stringPattern, String stringToCheck){
		if(stringToCheck == null){
			return false;
		}
		Pattern pattern = Pattern.compile(stringPattern);
        Matcher matcher = pattern.matcher(stringToCheck);
        if(matcher.matches()) {
            return true;
        }
        return false;
	}
	
	/**
	 * Check if a given string represents a date,based on current locale.
	 * @param stringToCheck
	 * @return
	 */
	public static boolean isDate(String stringToCheck){
		return checkString(getRegexOfCurrentLocale(), stringToCheck);
	}
	
	/**
	 * Gets a DateTimeFormatter object based on the current locale.
	 * @return
	 */
	public static DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern(getPatternOfCurrentLocale(), LocaleContextHolder.getLocale());
	}
	
	/**
	 * Get the string pattern based on current locale.
	 * @return
	 */
	public static String getPatternOfCurrentLocale(){
		
		Locale locale = LocaleContextHolder.getLocale();
		
		if(locale.getLanguage().equalsIgnoreCase("fr")) {
			return FORMAT_DATE_FR;
		}
		else if(locale.getLanguage().equalsIgnoreCase("en")) {
			return FORMAT_DATE_EN;
		}
		else if(locale.getLanguage().equalsIgnoreCase("es")) {
			return FORMAT_DATE_ES;
		}
		/*
		 * If no locale found
		 */
		return FORMAT_DATE_EN;
	}
	
	/**
	 * Get the string regex based on current locale.
	 * @return
	 */
	public static String getRegexOfCurrentLocale(){
		
		Locale locale = LocaleContextHolder.getLocale();
		
		if(locale.getLanguage().equalsIgnoreCase("fr")) {
			return REGEX_DATE_FR;
		}
		else if(locale.getLanguage().equalsIgnoreCase("en")) {
			return REGEX_DATE_EN;
		}
		else if(locale.getLanguage().equalsIgnoreCase("es")) {
			return REGEX_DATE_ES;
		}
		/*
		 * If no locale found
		 */
		return REGEX_DATE_EN;
	}
	
	/**
	 * Converts a String to a LocalDateTime object.
	 * @param strRecuperee
	 * @return
	 */
	public static LocalDateTime convert(String strRecuperee){
		
		if(strRecuperee==null || !isDate(strRecuperee)) {
			return null;
		}
		return LocalDate.parse(strRecuperee, getDateTimeFormatter()).atTime(0, 0);
	}
	
	/**
	 * Converts a LocalDateTime object to a String.
	 * @param localDate
	 * @return
	 */
	public static String convert(LocalDateTime localDate) {
		
		if (localDate != null) {
			return localDate.format( getDateTimeFormatter() );
		}
		return "";
	}
	
	/**
	 * Converts a LocalDateTime object to a java.sql.Timestamp object.
	 * @param date
	 * @return
	 */
	public static Timestamp getTimestamp(LocalDateTime date){
		if(date != null) {
			return Timestamp.valueOf(date.withHour(0).withMinute(0).withSecond(0));
		}
		else {
			return null;
		}
	}
	
	/**
	 * Converts a java.sql.Timestamp object to a LocalDateTime object.
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
		
		if(timestamp != null) {
			return timestamp.toLocalDateTime();
		}
		return null;
	}

}
