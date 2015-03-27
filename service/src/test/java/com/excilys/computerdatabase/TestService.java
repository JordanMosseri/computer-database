package com.excilys.computerdatabase;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistance.ICompanyDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.service.ICompanyService;
import com.excilys.computerdatabase.service.IComputerService;
import com.excilys.computerdatabase.service.impl.ComputerService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/serviceContext.xml"})
public class TestService {
	
	//@Autowired
	@InjectMocks
	ComputerService computerService;
	
	@Mock
	IComputerDAO computerDAO;
	
	@Mock
	ICompanyDAO companyDAO;
	
	@Before
	public void before() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	//@Ignore
	public void testService1(){
		//ComputerDAO dao = Mockito.mock(ComputerDAO.class);
		
		List<Computer> computers = new ArrayList<Computer>();
		computers.add(new Computer(-1, "computerMocked", null, null, new Company(1)));
		
		Mockito.when(computerDAO.findAll("")).thenReturn(computers);
		
		List<Computer> computersFromService = computerService.getComputers();
		Assert.assertEquals("computerMocked", computersFromService.get(0).getName());
	}
	
}
