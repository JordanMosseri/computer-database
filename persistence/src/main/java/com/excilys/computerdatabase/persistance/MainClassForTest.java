package com.excilys.computerdatabase.persistance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClassForTest {

	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/*parsistenceContext.xml");
		IComputerDAO computerDAO = (IComputerDAO) context.getBean("computerDAO");
		
		computerDAO.findAll("");
		
	}
	
}
