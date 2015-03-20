package com.excilys.computerdatabase.main;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistance.ComputerDAO;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Utils;

public class TestService {
	
	
	
	@Before
	public void before() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void after(){
		
	}
	
	@Test
	public void testCheckString(){
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "02"));
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "0"));
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "1"));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, " "));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, ""));
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "12"));
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "123"));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, "12.3"));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, "12a3"));
		assertTrue(Utils.checkString(Utils.REGEX_INTEGER, "-1"));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, "-1.2"));
		assertFalse(Utils.checkString(Utils.REGEX_INTEGER, null));
	}
	
	@Test
	@Ignore
	public void testService1(){
		ComputerDAO dao = Mockito.mock(ComputerDAO.class);
		
		List<Computer> computers = new ArrayList<Computer>();
		computers.add(new Computer(-1, "computerMocked", null, null, new Company(1)));
		//Mockito.when(dao.INSTANCE.getAll("", DAOUtils.getConnexion())).thenReturn(computers);
		
		List<Computer> computersFromService = (new ComputerService()).getComputers();
		for (Computer computer : computersFromService) {
			Assert.assertEquals("computerMocked", computer);
		}
	}
	
	/*@Test
	public void testStringToInt(){
		assertEquals(1, NumberUtils.toInt("1"));
		assertEquals(12, NumberUtils.toInt("12"));
		assertEquals(-1, NumberUtils.toInt("a"));
		assertEquals(-1, NumberUtils.toInt("5a"));
	}*/
	
	@Test
	public void test3(){
		
	}
}
