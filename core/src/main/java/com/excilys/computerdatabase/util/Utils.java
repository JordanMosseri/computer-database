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
	
	//new SimpleDateFormat(Utils.FORMAT_DATE)//avant LocalDateTime
	//public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
	
	//"[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"
	public static final String REGEX_DATE_EN = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	public static final String REGEX_DATE_FR = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
	public static final String REGEX_DATE_ES = "[0-9]{2}/[0-9]{2}/[0-9]{4}";
	
	public static final String REGEX_INTEGER = "[+-]?[0-9]++";
	
	public static boolean checkString(String motif, String chaine){
		if(chaine == null){
			return false;
		}
		Pattern pattern = Pattern.compile(motif);
        Matcher matcher = pattern.matcher(chaine);
        if(matcher.matches()) {
            return true;
        }
        return false;
	}
	
	public static boolean isDate(String chaine){
		return checkString(getRegexOfCurrentLocale(), chaine);
	}
	
	public static DateTimeFormatter getDateTimeFormatter() {
		
		return DateTimeFormatter.ofPattern(getPatternOfCurrentLocale(), LocaleContextHolder.getLocale());
		
		//return DateTimeFormatter.ISO_DATE;
	}
	
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
		//TODO default
		return "";
	}
	
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
		//TODO default
		return "";
	}
	
	public static LocalDateTime convert(String strRecuperee){
		
		if(strRecuperee==null || !isDate(strRecuperee)) {
			return null;
		}
		//TODO faire avec time dans pattern direct?
		return LocalDate.parse(strRecuperee, getDateTimeFormatter()).atTime(0, 0);
	}
	
	public static String convert(LocalDateTime localDate) {
		
		if (localDate != null) {
			//Constantes.dateFormat.format(c.dateAdded);//avant LocalDateTime
			//DateTimeFormatter.ofPattern(Utils.FORMAT_DATE)
			return localDate.format( getDateTimeFormatter() );
		}
		return "";
	}
	
	public static Timestamp getTimestamp(LocalDateTime date){
		if(date != null) {
			return Timestamp.valueOf(date.withHour(0).withMinute(0).withSecond(0));
		}
		else {
			return null;
		}
	}
	
	public static LocalDateTime getLocalDateTime(Timestamp timestamp) {
		
		if(timestamp != null) {
			return timestamp.toLocalDateTime();
		}
		return null;
	}

}
