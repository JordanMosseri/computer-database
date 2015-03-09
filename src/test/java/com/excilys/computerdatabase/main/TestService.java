package com.excilys.computerdatabase.main;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistance.DAOUtils;
import com.excilys.computerdatabase.persistance.ComputerDAO;
import com.excilys.computerdatabase.service.Service;
import com.excilys.computerdatabase.util.Constantes;

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
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "02"));
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "0"));
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "1"));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, " "));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, ""));
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "12"));
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "123"));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, "12.3"));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, "12a3"));
		assertTrue(Service.checkString(Constantes.REGEX_INTEGER, "-1"));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, "-1.2"));
		assertFalse(Service.checkString(Constantes.REGEX_INTEGER, null));
	}
	
	@Test
	@Ignore
	public void testService1(){
		ComputerDAO dao = Mockito.mock(ComputerDAO.class);
		
		List<Computer> computers = new ArrayList<Computer>();
		computers.add(new Computer(-1, "computerMocked", null, null, new Company(1)));
		Mockito.when(dao.INSTANCE.getAll("", DAOUtils.getConnexion())).thenReturn(computers);
		
		List<Computer> computersFromService = (new Service()).getComputers();
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
