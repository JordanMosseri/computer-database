package com.excilys.computerdatabase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
	
	@Autowired
	CompanyPaginationRep companyDAO;
	
	
	
	@Before
	public void before(){
		UtilsForTests.regenerateDB();
		UtilsForTests.resetExpectedArrays();
	}
	
	@Test
	public void testFindOne(){
		Computer computer = computerDAO.findOne(3);
		
		Assert.assertEquals(UtilsForTests.computersExpected[2], computer);
		Assert.assertEquals(UtilsForTests.computersExpected[2].getCompany(), computer.getCompany());
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
		UtilsForTests.computersExpected[1].setDateAdded(DateMapper.convert("2015-06-03"));
		
		computerDAO.save(UtilsForTests.computersExpected[1]);
		
		Assert.assertEquals(UtilsForTests.computersExpected[1], computerDAO.findOne(2));
	}
	
	@Test
	public void testFindAll(){
		List<Computer> computers = computerDAO.findAll();
		
		Assert.assertEquals(UtilsForTests.COMPUTERS_COUNT, computers.size());
		
		for (int i = 0; i < UtilsForTests.computersExpected.length; i++) {
			Assert.assertEquals(UtilsForTests.computersExpected[i], computers.get(i));
			Assert.assertEquals(UtilsForTests.computersExpected[i].getCompany(), computers.get(i).getCompany());
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
		Assert.assertEquals(UtilsForTests.COMPUTERS_COUNT+1, count);
	}
	
	@Test
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
		Assert.assertEquals(UtilsForTests.COMPUTERS_COUNT+1, count);
	}
	
	@Test(expected = Exception.class)
	public void testInsertWithWrongCompanyId(){
		
		Computer computer = new Computer(-1, "abcd", null, null, new Company(200));
		computerDAO.save(computer);
	}
	
	@Test
	public void testInsertWithNullCompany(){
		
		Computer computer = new Computer(-1, "abcd", null, null, null);
		computerDAO.save(computer);
	}
	
	@Test(expected = Exception.class)
	public void testInsertWithNegativeCompanyId(){
		
		Computer computer = new Computer(-1, "abcd", null, null, new Company(-1));
		computerDAO.save(computer);
	}
	
	@Test(expected = EmptyResultDataAccessException.class)
	public void testDeleteWrong(){
		int id = UtilsForTests.LAST_COMPUTER_ID+100;
		
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
		Assert.assertFalse(computerDAO.exists(UtilsForTests.LAST_COMPUTER_ID+100));
	}
	
	@Test
	public void testCount(){
		Assert.assertEquals(UtilsForTests.COMPUTERS_COUNT, computerDAO.count());
	}
	
	@Test
	public void testUpdateThenFindOne() {
		UtilsForTests.computersExpected[2].getCompany().getComputers().remove(UtilsForTests.computersExpected[2]);
		UtilsForTests.computersExpected[2].setCompany(null);
		computerDAO.saveAndFlush(UtilsForTests.computersExpected[2]);
		
		Assert.assertEquals(UtilsForTests.computersExpected[0], computerDAO.findOne(1));
		Assert.assertEquals(UtilsForTests.computersExpected[2], computerDAO.findOne(3));
	}
	
}
