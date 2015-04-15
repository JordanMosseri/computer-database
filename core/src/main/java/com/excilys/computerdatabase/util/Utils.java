package com.excilys.computerdatabase.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility functions
 * @author Jordan Mosseri
 *
 */
public class Utils {

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
		return checkString(LocaleUtils.getRegexOfCurrentLocale(), stringToCheck);
	}
	
}
