package main;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modele.Computer;
import modele.Company;
import dao.CompanyDAO;
import dao.ComputerDAO;



public class Service {
	
	
	
	
	
	public static void main(String[] args) {
		
		Service controleur = new Service();
		View vue = new View();
		
		controleur.vue = vue;
		vue.controleur = controleur;
		
		controleur.lancerProgramme();
		
		//System.out.println(verifierChaine(MOTIF, "20155-06-99"));
	}
	
	public  View vue;
	
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
				updateComputer();
				break;
			case 6:
				deleteComputer();
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
		List<Company> p = CompanyDAO.getInstance().getListCompanies();
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
	
	public void updateComputer(){
		int id = vue.recupIntFromConsole("Veuillez entrer un id d'ordi: ");
		String nomRecupered = vue.recupStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
		if( ! nomRecupered.equals(""))
			ComputerDAO.getInstance().updateComputer(id, nomRecupered);
		Date dateRecupered = vue.getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRecupered != null){
			ComputerDAO.getInstance().updateComputer(id, dateRecupered);
		}
	}
	
	public void deleteComputer(){
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
