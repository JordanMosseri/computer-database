package com.excilys.computerdatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistance.DAOUtils;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.util.Utils;

import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/persistenceContext.xml"})
public class TestComputerDAO {
	
	@Autowired
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
		Computer c = computerDAO.getOne(3);
		
		Assert.assertEquals("computer3", c.getName());
		Assert.assertNull(c.getDateAdded());
		Assert.assertNull(c.getDateRemoved());
		Assert.assertEquals(1, c.getCompany().getId());
		Assert.assertEquals("company1", c.getCompany().getName());
	}
	
	@Test
	public void testGetUnexistant() {
		
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
		List<Computer> computers = computerDAO.findAll("");
		
		Assert.assertEquals(COMPUTERS_COUNT, computers.size());
		
		Computer firstComputer = computers.get(0);
		Assert.assertEquals("computer1", firstComputer.getName());
		Assert.assertNull(firstComputer.getDateAdded());
		Assert.assertNull(firstComputer.getDateRemoved());
		Assert.assertEquals(1, firstComputer.getCompany().getId());
		Assert.assertEquals("company1", firstComputer.getCompany().getName());
		
		Computer lastComputer = computers.get(computers.size()-1);
		Assert.assertEquals("computer5", lastComputer.getName());
		Assert.assertNull(lastComputer.getDateAdded());
		Assert.assertNull(lastComputer.getDateRemoved());
		Assert.assertEquals(3, lastComputer.getCompany().getId());
		Assert.assertEquals("company3", lastComputer.getCompany().getName());
	}
	
	@Test
	public void testInsert(){
		String computerName = "computerTestWithCompanyName";
		LocalDateTime dateAdded = Utils.convert("2012-06-03");
		LocalDateTime dateRemoved = Utils.convert("2012-07-04");
		int companyId = 2;
		
		//insertion
		Computer c = new Computer(-1, computerName, dateAdded, dateRemoved, new Company(companyId));
		boolean result = computerDAO.save(c);
		Assert.assertTrue(result);
		
		//check if well added
		List<Computer> l = computerDAO.findAll("");
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertEquals(computerName, lastComputer.getName());
		Assert.assertEquals(dateAdded, lastComputer.getDateAdded());
		Assert.assertEquals(dateRemoved, lastComputer.getDateRemoved());
		Assert.assertEquals(companyId, lastComputer.getCompany().getId());
		
		//check total count
		int count = computerDAO.count();
		Assert.assertEquals(COMPUTERS_COUNT+1, count);
	}
	
	@Test(expected = ArithmeticException.class)  
	public void divisionWithException() {  
	  int i = 1/0;
	}
	
	//com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
	//java.sql.SQLIntegrityConstraintViolationException
	//java.sql.SQLException
	
	@Test(expected = Exception.class)
	//@Ignore
	//TODO
	public void testInsertWithWrongCompanyId(){
		String computerName = "computerTestNotHere";
		LocalDateTime dateAdded = Utils.convert("2012-06-03");
		LocalDateTime dateRemoved = Utils.convert("2012-07-04");
		int companyId = 200;
		
		//insertion
		Computer c = new Computer(-1, computerName, dateAdded, dateRemoved, new Company(companyId));
		boolean result = computerDAO.save(c);
		Assert.assertFalse(result);
		
		//check if well added
		List<Computer> l = computerDAO.findAll("");
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertNotEquals(computerName, lastComputer.getName());
		Assert.assertNotEquals(dateAdded, lastComputer.getDateAdded());
		Assert.assertNotEquals(dateRemoved, lastComputer.getDateRemoved());
		Assert.assertNotEquals(companyId, lastComputer.getCompany().getId());
		
		//check total count
		int count = computerDAO.count();
		Assert.assertEquals(COMPUTERS_COUNT, count);
	}
	
	@Test
	public void testDeleteWrong(){
		int id = LAST_COMPUTER_ID+100;
		
		boolean result = computerDAO.delete(id);
		
		Assert.assertFalse(result);
	}
	
	@Test
	public void testDelete(){
		int id = 4;
		
		computerDAO.delete(id);
		
		Assert.assertFalse(computerDAO.exists(id));
	}
	
	@Test
	public void testExists(){
		Assert.assertTrue(computerDAO.exists(3));
	}
	
	@Test
	public void testNotExists(){
		Assert.assertFalse(computerDAO.exists(LAST_COMPUTER_ID+100));
	}
	
	@Test
	public void testTotal(){
		Assert.assertEquals(COMPUTERS_COUNT, computerDAO.count());
	}
	
}
