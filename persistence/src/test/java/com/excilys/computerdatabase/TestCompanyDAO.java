package com.excilys.computerdatabase;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;

/**
 * 
 * @author Jordan Mosseri
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/persistenceContextForTests.xml"})
public class TestCompanyDAO {
	
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
	//@Transactional
	public void testSave() {
		companyDAO.save(new Company("abcd yahou"));
		
		Company company = companyDAO.findOne(5);
		//Assert.assertEquals(new Company("abcd yahou", 5), company);
		Assert.assertEquals("abcd yahou", company.getName());
	}
	
	@Test
	public void testExists() {
		
	}
	
	@Test
	public void testFindByName() {
		Company company = companyDAO.findByName(UtilsForTests.companiesExpected[1].getName());
		
		Assert.assertEquals(UtilsForTests.companiesExpected[1], company);
	}
	
	@Test
	public void testFindByNameUnexistent() {
		Company company = companyDAO.findByName("hello, I am a company that doesn't exist");
		
		Assert.assertNull(company);
	}
	
	@Test
	public void testDelete() {
		companyDAO.delete(2);
		
		Assert.assertFalse(companyDAO.exists(2));
	}
	
	@Test
	public void testDeleteWithDependances() {
		int idToDelete = 3;
		
		//Delete computers linked to the company first (avoid exception due to constraint key)
		List<Computer> computers = computerDAO.findAll();
		for (Computer computer : computers) {
			if(computer.getCompany().getId() == idToDelete) {
				computerDAO.delete(computer);
			}
		}
		
		//Delete company
		companyDAO.delete(idToDelete);
		
		Assert.assertFalse(companyDAO.exists(idToDelete));
	}
	
}
