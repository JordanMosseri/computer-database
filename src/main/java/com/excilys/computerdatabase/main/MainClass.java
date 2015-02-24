package com.excilys.computerdatabase.main;

import com.excilys.computerdatabase.util.Logging;

public class MainClass {

	
	public static void main(String[] args) {
		Logging.log("salut !");
		
		Service controleur = new Service();
		View vue = new View();
		
		controleur.vue = vue;
		vue.controleur = controleur;
		
		controleur.lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
	}
	
}
