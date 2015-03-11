package com.excilys.computerdatabase.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistance.DAOUtils;
import com.excilys.computerdatabase.persistance.ComputerDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.service.Service;

public class TestComputerDAO {
	
	@Autowired
	@Qualifier(value = "ComputerDAO")
	IComputerDAO computerDAO;
	
	public static final int COMPUTERS_COUNT = 5;
	public static final int LAST_COMPUTER_ID = 5;
	
	public static void regenerateDB(){
		//--user=admincdb
		try {
			String line;
			Process p = Runtime.getRuntime().exec(
					"/home/excilys/workspace_jee/ComputerdatabaseMaven/src/main/resources/script.sh"
					);
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/*@BeforeClass
	public static void beforeClass(){
		//ComputerDAO.INSTANCE.URL = ComputerDAO.INSTANCE.URL.replaceAll("computer-database-db", "computer-database-db-tests");
		//CompanyDAO.INSTANCE.URL = CompanyDAO.INSTANCE.URL.replaceAll("computer-database-db", "computer-database-db-tests");
	}*/
	
	@Before
	public void before(){
		regenerateDB();
		DAOUtils.UNIT_TEST = true;
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	public void testGet(){
		Computer c = computerDAO.get(3, DAOUtils.getConnexion());
		
		Assert.assertEquals("computer3", c.name);
		Assert.assertNull(c.dateAdded);
		Assert.assertNull(c.dateRemoved);
		Assert.assertEquals(1, c.company.id);
		Assert.assertEquals("company1", c.company.name);
	}
	
	@Test
	public void testGetWithNonNullDate(){
		
	}
	
	@Test
	public void testGetPart(){
		
	}
	
	@Test
	public void testUpdate(){
		
	}
	
	@Test
	public void testGetAll(){
		List<Computer> computers = computerDAO.getAll("", DAOUtils.getConnexion());
		
		Assert.assertEquals(COMPUTERS_COUNT, computers.size());
		
		Computer firstComputer = computers.get(0);
		Assert.assertEquals("computer1", firstComputer.name);
		Assert.assertNull(firstComputer.dateAdded);
		Assert.assertNull(firstComputer.dateRemoved);
		Assert.assertEquals(1, firstComputer.company.id);
		Assert.assertEquals("company1", firstComputer.company.name);
		
		Computer lastComputer = computers.get(computers.size()-1);
		Assert.assertEquals("computer5", lastComputer.name);
		Assert.assertNull(lastComputer.dateAdded);
		Assert.assertNull(lastComputer.dateRemoved);
		Assert.assertEquals(3, lastComputer.company.id);
		Assert.assertEquals("company3", lastComputer.company.name);
	}
	
	@Test
	public void testInsert(){
		String computerName = "computerTestWithCompanyName";
		LocalDateTime dateAdded = Service.parse("2012-06-03");
		LocalDateTime dateRemoved = Service.parse("2012-07-04");
		int companyId = 2;
		
		//insertion
		Computer c = new Computer(-1, computerName, dateAdded, dateRemoved, new Company(companyId));
		boolean result = computerDAO.insert(c, DAOUtils.getConnexion());
		Assert.assertTrue(result);
		
		//check if well added
		List<Computer> l = computerDAO.getAll("", DAOUtils.getConnexion());
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertEquals(computerName, lastComputer.name);
		Assert.assertEquals(dateAdded, lastComputer.dateAdded);
		Assert.assertEquals(dateRemoved, lastComputer.dateRemoved);
		Assert.assertEquals(companyId, lastComputer.company.id);
		
		//check total count
		int count = computerDAO.getTotalCount(DAOUtils.getConnexion());
		Assert.assertEquals(COMPUTERS_COUNT+1, count);
	}
	
	@Test(expected = ArithmeticException.class)  
	public void divisionWithException() {  
	  int i = 1/0;
	}
	
	//com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
	//java.sql.SQLIntegrityConstraintViolationException
	//java.sql.SQLException
	
	@Test//(expected = java.sql.SQLException.class)
	//@Ignore
	public void testInsertWithWrongCompanyId(){
		String computerName = "computerTestNotHere";
		LocalDateTime dateAdded = Service.parse("2012-06-03");
		LocalDateTime dateRemoved = Service.parse("2012-07-04");
		int companyId = 200;
		
		//insertion
		Computer c = new Computer(-1, computerName, dateAdded, dateRemoved, new Company(companyId));
		boolean result = computerDAO.insert(c, DAOUtils.getConnexion());
		Assert.assertFalse(result);
		
		//check if well added
		List<Computer> l = computerDAO.getAll("", DAOUtils.getConnexion());
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertNotEquals(computerName, lastComputer.name);
		Assert.assertNotEquals(dateAdded, lastComputer.dateAdded);
		Assert.assertNotEquals(dateRemoved, lastComputer.dateRemoved);
		Assert.assertNotEquals(companyId, lastComputer.company.id);
		
		//check total count
		int count = computerDAO.getTotalCount(DAOUtils.getConnexion());
		Assert.assertEquals(COMPUTERS_COUNT, count);
	}
	
	@Test
	public void testDeleteWrong(){
		int id = LAST_COMPUTER_ID+100;
		
		boolean result = computerDAO.delete(id, DAOUtils.getConnexion());
		
		Assert.assertFalse(result);
	}
	
	@Test
	public void testDelete(){
		int id = 4;
		
		computerDAO.delete(id, DAOUtils.getConnexion());
		
		Assert.assertFalse(computerDAO.exists(id, DAOUtils.getConnexion()));
	}
	
	@Test
	public void testExists(){
		Assert.assertTrue(computerDAO.exists(3, DAOUtils.getConnexion()));
	}
	
	@Test
	public void testNotExists(){
		Assert.assertFalse(computerDAO.exists(LAST_COMPUTER_ID+100, DAOUtils.getConnexion()));
	}
	
	@Test
	//@Ignore
	public void testTotal(){
		Assert.assertEquals(COMPUTERS_COUNT, computerDAO.getTotalCount(DAOUtils.getConnexion()));
	}
	
}
