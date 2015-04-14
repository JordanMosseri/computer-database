package com.excilys.computerdatabase.util;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleUtils {
	
	private static final String FORMAT_DATE_EN = "yyyy-MM-dd";
	private static final String FORMAT_DATE_FR = "dd-MM-yyyy";
	private static final String FORMAT_DATE_ES = "dd/MM/yyyy";
	
	private static final String REGEX_DATE_EN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	private static final String REGEX_DATE_FR = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
	private static final String REGEX_DATE_ES = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	
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
	
}
