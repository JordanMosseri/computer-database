package main;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.CompanyDAO;
import dao.ComputerDAO;
import modele.*;



public class Controleur {
	
	
	
	
	
	public static void main(String[] args) {
		
		Controleur controleur = new Controleur();
		Vue vue = new Vue();
		
		controleur.vue = vue;
		vue.controleur = controleur;
		
		controleur.lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
	}
	
	public  Vue vue;
	
	public  void lancerProgramme() {
		
		boolean continuer = true;
		
		while(continuer){
			
			int selection = vue.afficherMenu();
			
			
			switch (selection) {
			case 1:
				listerComputers();
				break;
			case 2:
				listerCompanies();
				break;
			case 3:
				showComputerDetails();
				break;
			case 4:
				creerComputer();
				break;
			case 5:
				update();
				break;
			case 6:
				delete();
				break;
			case 7:
				continuer=false;
				vue.println("Au revoir...");
				break;
			default:
				break;
			}
		}
		
		
	}
	
	public void listerComputers(){
		List<Computer> p = ComputerDAO.getInstance().getListComputers();
		vue.println(p);
	}
	
	public void listerCompanies(){
		List<Fabriquant> p = CompanyDAO.getInstance().getListCompanies();
		vue.println(p);
	}
	
	public void showComputerDetails(){
		Computer c = ComputerDAO.getInstance().getComputer(vue.recupIntFromConsole("Veuillez entrer un id d'ordi: "));
		vue.println(c);
	}
	
	public void creerComputer(){
		ComputerDAO.getInstance().insereComputer(vue.recupOrdiFromConsole());
		listerComputers();
	}
	
	public void update(){
		int id = vue.recupIntFromConsole("Veuillez entrer un id d'ordi: ");
		String nomRecupered = vue.recupStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
		if( ! nomRecupered.equals(""))
			ComputerDAO.getInstance().updateComputer(id, nomRecupered);
		Date dateRecupered = vue.getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRecupered != null){
			ComputerDAO.getInstance().updateComputer(id, dateRecupered);
		}
	}
	
	public void delete(){
		int id2 = vue.recupIntFromConsole("Veuillez entrer un id d'ordi: ");
		ComputerDAO.getInstance().deleteComputer(id2);
	}
	
	
	
	public boolean verifierChaine(String motif, String chaine){
		Pattern pattern = Pattern.compile(motif);
        Matcher matcher = pattern.matcher(chaine);
        if(matcher.matches()) {
            return true;
        }
        return false;
	}
	
	
// String xxx(message,messageError,regex)
}
