package com.excilys.computerdatabase.cli;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Holds the main() function
 * @author Jordan Mosseri
 *
 */
public class MainClass {

	/**
	 * Entry point for the console program
	 * @param args
	 */
	public static void main(String[] args) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/consoleContext.xml");
		IView view = (IView) context.getBean("view");
		
		view.runProgram();
		
		context.close();
	}
	
}
