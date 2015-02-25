package com.excilys.computerdatabase.main;

import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Logging;

public class MainClass {

	
	public static void main(String[] args) {
		
		
		Service controleur = new Service();
		View view = new View();
		
		controleur.view = view;
		view.controleur = controleur;
		
		view.lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
	}
	
}
