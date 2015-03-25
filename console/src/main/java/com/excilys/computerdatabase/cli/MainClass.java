package com.excilys.computerdatabase.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {

	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		IView view = (IView) context.getBean("view");
		
		view.lancerProgramme();
	}
	
}
