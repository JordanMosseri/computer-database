package com.excilys.computerdatabase.util;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Constantes {
	
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.FORMAT_DATE);
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	//"[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"
	public static final String REGEX_DATE = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
	
	public static final String REGEX_INTEGER = "[+-]?[0-9]++";
	
	
	
}
