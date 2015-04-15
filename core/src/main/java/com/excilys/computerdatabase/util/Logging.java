package com.excilys.computerdatabase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Central Logger
 * @author Jordan Mosseri
 *
 */
public class Logging {
	
	public final static Logger logger = LoggerFactory.getLogger("ComputerDatabaseLogger");
	
	public static void log(Object o){
		logger.debug(o.toString());
	}
}
