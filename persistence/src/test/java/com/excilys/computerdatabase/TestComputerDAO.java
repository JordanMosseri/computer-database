package com.excilys.computerdatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;

import com.excilys.computerdatabase.mappers.DateMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;
import com.excilys.computerdatabase.util.Utils;

import org.springframework.test.context.junit4.*;

/**
 * Tests for computer DAO's functions. Note that each time we compare, we need to compare both the computer and the computer's company 
 * (computer's equals() function doesn't compare the company object).
 * @author Jordan Mosseri
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/persistenceContextForTests.xml"})
public class TestComputerDAO {
	
	@Autowired
	ComputerPaginationRep computerDAO;
	
	//Expected data from the database
	
	static final int COMPUTERS_COUNT = 5;
	static final int LAST_COMPUTER_ID = 5;
	
	static Company[] companiesExpected;
	static Computer[] computersExpected;
	
	/**
	 * Used to reset the database with initial data, from an external script, before each test.
	 */
	public static void regenerateDB(){
		//--user=admincdb
		try {
			String line;
			//TODO recup bon fichier !!!
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
	
	public static void resetExpectedArrays() {
		
		companiesExpected = new Company[] {
				new Company("company1", 1),
				new Company("company2", 2),
				new Company("company3", 3),
				new Company("company4", 4)
		};
		
		computersExpected = new Computer[] {
				new Computer(1, "computer1", null, null, companiesExpected[0]),
				new Computer(2, "computer2", null, null, companiesExpected[0]),
				new Computer(3, "computer3", null, null, companiesExpected[0]),
				new Computer(4, "computer4", null, null, companiesExpected[2]),
				new Computer(5, "computer5", null, null, companiesExpected[2])
		};
		
	}
	
	@Before
	public void before(){
		regenerateDB();
		resetExpectedArrays();
	}
	
	@Test
	public void testFindOne(){
		Computer computer = computerDAO.findOne(3);
		
		Assert.assertEquals(computersExpected[2], computer);
		Assert.assertEquals(computersExpected[2].getCompany(), computer.getCompany());
	}
	
	@Test
	public void testFindUnexistant() {
		Computer computer = computerDAO.findOne(300);
		Assert.assertNull(computer);
	}
	
	@Test
	public void testGetWithNonNullDate(){
		
	}
	
	@Test
	public void testFindPart(){
		
	}
	
	@Test
	public void testFindByNameLike(){
		
	}
	
	@Test
	public void testUpdate(){
		computersExpected[1].setDateAdded(DateMapper.convert("2015-06-03"));
		
		computerDAO.save(computersExpected[1]);
		
		Assert.assertEquals(computersExpected[1], computerDAO.findOne(2));
	}
	
	@Test
	public void testFindAll(){
		List<Computer> computers = computerDAO.findAll();
		
		Assert.assertEquals(COMPUTERS_COUNT, computers.size());
		
		for (int i = 0; i < computersExpected.length; i++) {
			Assert.assertEquals(computersExpected[i], computers.get(i));
			Assert.assertEquals(computersExpected[i].getCompany(), computers.get(i).getCompany());
		}
	}
	
	@Test
	public void testInsert(){
		
		//Creates some computer
		Computer computerToAdd = new Computer(-1, "computerTestWithCompanyName", DateMapper.convert("2012-06-03"), DateMapper.convert("2012-07-04"), new Company(2));
		
		//Insert the computer
		Computer result = computerDAO.save(computerToAdd);
		
		Assert.assertEquals(computerToAdd, result);
		//Assert.assertEquals(computerToAdd.getCompany(), result.getCompany());
		
		//Get the last computer
		List<Computer> computers = computerDAO.findAll();
		Computer lastComputer = computers.get(computers.size() - 1);
		
		//Check if well added
		Assert.assertEquals(computerToAdd, lastComputer);
		Assert.assertEquals(computerToAdd.getCompany().getId(), lastComputer.getCompany().getId());
		//Assert.assertEquals(computerToAdd.getCompany(), lastComputer.getCompany());
		
		//Check total count
		int count = (int) computerDAO.count();
		Assert.assertEquals(COMPUTERS_COUNT+1, count);
	}
	
	@Test
	//@Ignore
	//TODO a finir/regler pb : TransientPropertyValueException: Not-null property references a transient value - transient instance must be saved before current operation
	//SOLUTION: 
	public void testInsertWithNewCompany() {
		
		//Creates some computer
		Computer computerToAdd = new Computer(-1, "computerTestWithCompanyName", DateMapper.convert("2012-06-03"), DateMapper.convert("2012-07-04"), new Company("hello I am a new company"));
		
		//Save the company
		Company companySaved = companyDAO.save(computerToAdd.getCompany());
		computerToAdd.setCompany(companySaved);
		computerToAdd.getCompany().getComputers().add(computerToAdd);
		
		//Insert the computer
		Computer result = computerDAO.save(computerToAdd);
		
		Assert.assertEquals(computerToAdd, result);
		
		//Get the last computer
		List<Computer> computers = computerDAO.findAll();
		Computer lastComputer = computers.get(computers.size() - 1);
		
		//Check if well added
		Assert.assertEquals(computerToAdd, lastComputer);
		Assert.assertEquals(computerToAdd.getCompany().getName(), lastComputer.getCompany().getName());
		
		//Check total count
		int count = (int) computerDAO.count();
		Assert.assertEquals(COMPUTERS_COUNT+1, count);
	}
	
	//com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
	//java.sql.SQLIntegrityConstraintViolationException
	//java.sql.SQLException
	
	@Test(expected = Exception.class)
	//@Ignore
	//TODO
	public void testInsertWithWrongCompanyId(){
		String computerName = "computerTestNotHere";
		LocalDateTime dateAdded = DateMapper.convert("2012-06-03");
		LocalDateTime dateRemoved = DateMapper.convert("2012-07-04");
		int companyId = 200;
		
		//insertion
		Computer c = new Computer(-1, computerName, dateAdded, dateRemoved, new Company(companyId));
		Computer result = computerDAO.save(c);
		Assert.assertNotEquals(c, result);
		
		//check if well added
		List<Computer> l = computerDAO.findAll();
		Computer lastComputer = l.get(l.size() - 1);
		
		Assert.assertNotEquals(computerName, lastComputer.getName());
		Assert.assertNotEquals(dateAdded, lastComputer.getDateAdded());
		Assert.assertNotEquals(dateRemoved, lastComputer.getDateRemoved());
		Assert.assertNotEquals(companyId, lastComputer.getCompany().getId());
		
		//check total count
		int count = (int) computerDAO.count();
		Assert.assertEquals(COMPUTERS_COUNT, count);
	}
	
	@Test(expected = RuntimeException.class)//EmptyResultDataAccessException
	public void testDeleteWrong(){
		int id = LAST_COMPUTER_ID+100;
		
		computerDAO.delete(id);
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
	public void testCount(){
		Assert.assertEquals(COMPUTERS_COUNT, computerDAO.count());
	}
	
	//TODO mettre dans une autre classe
	
	@Autowired
	CompanyPaginationRep companyDAO;
	
	@Test
	//@Transactional
	public void testCompanyDAOSave() {
		companyDAO.save(new Company("abcd yahou"));
		
		Company company = companyDAO.findOne(5);
		//Assert.assertEquals(new Company("abcd yahou", 5), company);
		Assert.assertEquals("abcd yahou", company.getName());
	}
	
	@Test
	public void testCompanyDAOExists() {
		
	}
	
}
