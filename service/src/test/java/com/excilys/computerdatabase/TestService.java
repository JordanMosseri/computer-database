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

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"/serviceContext.xml"})
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
	public void testService1() {
		List<Computer> computers = new ArrayList<Computer>();
		computers.add(new Computer(-1, "computerMocked", null, null, new Company(1)));
		
		Mockito.when(computerDAO.findAll()).thenReturn(computers);
		
		List<Computer> computersFromService = computerService.getComputers();
		Assert.assertEquals("computerMocked", computersFromService.get(0).getName());
	}
	
	@Test
	//TODO
	public void testServiceAdd() {
		
	}
	
}
