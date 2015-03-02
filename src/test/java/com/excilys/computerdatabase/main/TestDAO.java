package com.excilys.computerdatabase.main;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.util.Constantes;

import org.junit.Assert;

public class TestDAO {
	
	@BeforeClass
	public static void beforeClass(){
		ComputerDAO.getInstance().url = ComputerDAO.URL_TEST;
		CompanyDAO.getInstance().url = ComputerDAO.URL_TEST;
	}
	
	@Before
	public void after(){
		
	}
	
	@After
	public void before(){
		
	}
	
	@Test
	@Ignore
	public void testInsertComputer(){
		ComputerDAO.getInstance().insereComputer(new Computer(-1, "computerTestWithCompanyName", Service.stringToDate("2012-06-03"), Service.stringToDate("2012-07-04"), new Company("companyTest")));
		
		//check if well added
		List<Computer> l = ComputerDAO.getInstance().getAll();
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertEquals("computerTestWithCompanyName", lastComputer.name);
		Assert.assertEquals("2012-06-03", lastComputer.getDateAddedString());
		Assert.assertEquals("2012-07-04", lastComputer.getDateRemovedString());
		//company
	}
	
	@Test
	@Ignore
	public void testInsertComputer2(){
		ComputerDAO.getInstance().insereComputer(new Computer(-1, "computerTestWithCompanyId", Service.stringToDate("2012-06-03"), Service.stringToDate("2012-07-04"), new Company(1)));
	}
	
	@Test
	@Ignore
	public void testDeleteComputer(){
		ComputerDAO.getInstance().deleteComputer(18);
		
		Assert.assertFalse(ComputerDAO.getInstance().computerExists(18));
	}
	
}
