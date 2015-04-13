package com.excilys.computerdatabase.cli;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class MainClass {

	
	public static void main(String[] args) {
		
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath*:/consoleContext.xml");
		IView view = (IView) context.getBean("view");
		
		view.lancerProgramme();
		
		context.close();
	}
	
}
