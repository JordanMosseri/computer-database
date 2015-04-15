package com.excilys.computerdatabase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.excilys.computerdatabase.util.Utils;

public class TestUtils {
	
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

}
