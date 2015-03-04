package com.excilys.computerdatabase.main;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.*;

import static org.junit.Assert.*;

import com.excilys.computerdatabase.util.Constantes;

public class TestService {
	
	
	
	@Before
	public void before(){
		
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
	public void testStringToInt(){
		assertEquals(1, NumberUtils.toInt("1"));
		assertEquals(12, NumberUtils.toInt("12"));
		assertEquals(-1, NumberUtils.toInt("a"));
		assertEquals(-1, NumberUtils.toInt("5a"));
	}
	
	@Test
	public void test3(){
		
	}
}
