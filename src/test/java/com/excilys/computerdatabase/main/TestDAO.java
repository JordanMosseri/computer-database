package com.excilys.computerdatabase.main;

import java.time.LocalDateTime;
import java.util.Date;
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
		LocalDateTime dateAdded = Service.parse("2012-06-03");
		LocalDateTime dateRemoved = Service.parse("2012-07-04");
		
		(new Service()).addComputer(new Computer(-1, "computerTestWithCompanyName", dateAdded, dateRemoved, new Company("companyTest")));
		
		//check if well added
		List<Computer> l = ComputerDAO.getInstance().getAll();
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertEquals("computerTestWithCompanyName", lastComputer.name);
		Assert.assertEquals(dateAdded, lastComputer.dateAdded);
		Assert.assertEquals(dateRemoved, lastComputer.dateRemoved);
		//TODO company
	}
	
	@Test
	@Ignore
	public void testInsertComputer2(){
		(new Service()).addComputer(new Computer(-1, "computerTestWithCompanyId", Service.parse("2012-06-03"), Service.parse("2012-07-04"), new Company(1)));
		//TODO comme le 1
	}
	
	@Test
	@Ignore
	public void testDeleteComputer(){
		ComputerDAO.getInstance().deleteComputer(18);
		
		Assert.assertFalse(ComputerDAO.getInstance().computerExists(18));
	}
	
}
