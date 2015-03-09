package com.excilys.computerdatabase.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
	
	public final static Logger logger = LoggerFactory.getLogger("ComputerDatabaseLogger");
	static{
		//DOMConfigurator.configure("/home/excilys/workspace_jee/computerdatabase_test_maven/.../log4j-config.xml");
	}
	
	public static void log(Object o){
		logger.debug(o.toString());
	}
}
