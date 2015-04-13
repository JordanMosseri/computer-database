package com.excilys.computerdatabase.mappers;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.excilys.computerdatabase.util.LocaleUtils;
import com.excilys.computerdatabase.util.Utils;

/**
 * To Map String and LocalDate objects within model objects.
 * @author Jordan Mosseri
 *
 */
public class DateMapper {

	/**
	 * Converts a String to a LocalDateTime object.
	 * @param strRecuperee
	 * @return
	 */
	public static LocalDateTime convert(String strRecuperee){
		
		if(strRecuperee==null || !Utils.isDate(strRecuperee)) {
			return null;
		}
		return LocalDate.parse(strRecuperee, LocaleUtils.getDateTimeFormatter()).atTime(0, 0);
	}
	
	/**
	 * Converts a LocalDateTime object to a String.
	 * @param localDate
	 * @return
	 */
	public static String convert(LocalDateTime localDate) {
		
		if (localDate != null) {
			return localDate.format( LocaleUtils.getDateTimeFormatter() );
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
