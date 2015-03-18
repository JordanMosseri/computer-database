package com.excilys.computerdatabase.ui.cli;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class MainClass {

	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		IView view = (IView) context.getBean("view");
		
		view.lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
		
	}
	
}
