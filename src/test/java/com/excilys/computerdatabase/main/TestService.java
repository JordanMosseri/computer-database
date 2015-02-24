package com.excilys.computerdatabase.main;
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
		Service c = new Service();
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "02"));
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "0"));
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "1"));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, " "));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, ""));
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "12"));
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "123"));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, "12.3"));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, "12a3"));
		assertTrue(c.checkString(Constantes.REGEX_INTEGER, "-1"));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, "-1.2"));
		assertFalse(c.checkString(Constantes.REGEX_INTEGER, null));
	}
	
	@Test
	public void test2(){
		
	}
	
	@Test
	public void test3(){
		
	}
}
