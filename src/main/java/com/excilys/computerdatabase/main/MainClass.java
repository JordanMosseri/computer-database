package com.excilys.computerdatabase.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Logging;

public class MainClass {

	
	public static void main(String[] args) {
		
		//(new View()).lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
		
		String LINE_TO_EXEC;//--user=admincdb
		LINE_TO_EXEC = "/home/excilys/workspace_jee/computerdatabase_test_maven/src/main/resources/script.sh";
		try {
			String line;
			Process p = Runtime.getRuntime().exec(LINE_TO_EXEC);
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
	
}
