package com.excilys.computerdatabase;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

/**
 * Holds variables and methods used by test classes
 * @author Jordan Mosseri
 *
 */
public class UtilsForTests {
	
	//Expected data from the database
	
	static final int COMPUTERS_COUNT = 5;
	static final int LAST_COMPUTER_ID = 5;
	
	static Company[] companiesExpected;
	static Computer[] computersExpected;
	
	/**
	 * Used to reset the database with initial data, from an external script, before each test.
	 */
	public static void regenerateDB(){
		try {
			String line;
			Process p = Runtime.getRuntime().exec("src/test/resources/script.sh");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			input.close();
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	/**
	 * Reset arrays describing database contents
	 */
	public static void resetExpectedArrays() {
		
		companiesExpected = new Company[] {
				new Company("company1", 1),
				new Company("company2", 2),
				new Company("company3", 3),
				new Company("company4", 4)
		};
		
		computersExpected = new Computer[] {
				new Computer(1, "computer1", null, null, companiesExpected[0]),
				new Computer(2, "computer2", null, null, companiesExpected[0]),
				new Computer(3, "computer3", null, null, companiesExpected[0]),
				new Computer(4, "computer4", null, null, companiesExpected[2]),
				new Computer(5, "computer5", null, null, companiesExpected[2])
		};
	}
		
}
