package com.excilys.computerdatabase.util;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
	
	public final static Logger logger = LoggerFactory.getLogger("JMosseri_ComputerDatabase_Logger");
	static{
		//TODO faire en mode slf4j
		DOMConfigurator.configure("log4j-config.xml");
	}
	
	public static void log(Object o){
		logger.debug(o.toString());
	}
}