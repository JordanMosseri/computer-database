package com.excilys.computerdatabase;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;
import com.excilys.computerdatabase.service.impl.ComputerService;

/**
 * Tests for the service
 * @author Jordan Mosseri
 *
 */
public class TestService {
	
	//@Autowired
	@InjectMocks
	ComputerService computerService;
	
	@Mock
	ComputerPaginationRep computerDAO;
	
	@Mock
	CompanyPaginationRep companyDAO;
	
	@Before
	public void before() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	public void testServiceGetAll() {
		
		//List to fake
		List<Computer> computers = new ArrayList<Computer>();
		computers.add(new Computer(-1, "computerMocked", null, null, new Company(1)));
		computers.add(new Computer(-1, "computerMocked number 2", null, null, new Company("abcd", 2)));
		
		//Mock DAOs functions
		Mockito.when(computerDAO.findAll()).thenReturn(computers);
		
		//Check if everything fine
		List<Computer> computersFromService = computerService.getComputers();
		Assert.assertEquals(computers, computersFromService);
	}
	
	@Test
	public void testGetOne() {
		
		//Computer to fake
		Computer computer = new Computer(2, "computerMocked", null, null, new Company(1));
		
		//Mock DAOs functions
		Mockito.when(computerDAO.findOne(computer.getId())).thenReturn(computer);
		
		//Check if everything fine
		Computer computerFromService = computerService.getComputer(computer.getId());
		Assert.assertEquals(computer, computerFromService);
	}
	
	@Test
	public void testServiceAddWithNullCompany() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, null);
		
		computerService.addComputer(computer);
	}
	
	@Test
	public void testServiceAddWithNegativeCompanyId() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, new Company(-1));
		
		//Mock DAOs functions
		Mockito.when(computerDAO.save(computer)).thenReturn(computer);
		
		computerService.addComputer(computer);
	}
	
	/**
	 * Company id provided, exists in db
	 */
	@Test
	public void testServiceAdd() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, new Company(1));
		
		//Mock DAOs functions
		Mockito.when(companyDAO.exists(computer.getCompany().getId())).thenReturn(true);
		Mockito.when(computerDAO.save(computer)).thenReturn(computer);
		
		//Check if everything fine
		boolean result = computerService.addComputer(computer);
		Assert.assertTrue(result);
	}
	
	/**
	 * Company name provided, doesn't exists, attempt to create it
	 */
	@Test
	public void testServiceAddWithNewCompany() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, new Company("hello, I am a new company to be added"));
		
		//Mock DAOs functions
		Mockito.when(companyDAO.exists(computer.getCompany().getId())).thenReturn(false);
		Mockito.when(companyDAO.findByName(computer.getCompany().getName())).thenReturn(null);
		Mockito.when(companyDAO.save(computer.getCompany())).thenReturn(new Company(computer.getCompany().getName(), 50));
		Mockito.when(companyDAO.exists(50)).thenReturn(true);
		Mockito.when(computerDAO.save(computer)).thenReturn(computer);
		
		//Check if everything fine
		boolean result = computerService.addComputer(computer);
		Assert.assertTrue(result);
	}
	
	/**
	 * Company name provided, exists in db
	 */
	@Test
	public void testServiceAddWithExistantCompany() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, new Company("hello, I am an existant company"));
		
		//Mock DAOs functions
		Mockito.when(companyDAO.exists(computer.getCompany().getId())).thenReturn(false);
		Mockito.when(companyDAO.findByName(computer.getCompany().getName())).thenReturn(new Company(computer.getCompany().getName(), 25));
		Mockito.when(companyDAO.exists(25)).thenReturn(true);
		Mockito.when(computerDAO.save(computer)).thenReturn(computer);
		
		//Check if everything fine
		boolean result = computerService.addComputer(computer);
		Assert.assertTrue(result);
	}
	
	/**
	 * Company id provided, doesn't exists, an error happen
	 */
	@Test(expected = IllegalStateException.class)
	public void testServiceAddWithUnexistantCompanyError() {
		
		//Computer to add
		Computer computer = new Computer(-1, "computerMocked", null, null, new Company(12));
		
		//Mock DAOs functions
		Mockito.when(companyDAO.exists(12)).thenReturn(false);
		
		//Attempt to call addComputer()
		computerService.addComputer(computer);
	}
	
	@Test
	public void testGetPart() {
		
	}
	
}
