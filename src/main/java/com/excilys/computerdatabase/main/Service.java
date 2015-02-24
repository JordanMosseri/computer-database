package com.excilys.computerdatabase.main;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

import com.excilys.computerdatabase.util.Constantes;

public class Service {
	
	
	public  View vue;
	
	public  void lancerProgramme() {
		
		boolean continuer = true;
		
		while(continuer){
			
			int selection = vue.showMenuAndGetChoice();
			
			
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
		Computer c = ComputerDAO.getInstance().getComputer(vue.getIntFromConsole("Veuillez entrer un id d'ordi: "));
		vue.println(c);
	}
	
	public void creerComputer(){
		ComputerDAO.getInstance().insereComputer(vue.getComputerFromConsole());
		listerComputers();
	}
	
	public void updateComputer(){
		int id = vue.getIntFromConsole("Veuillez entrer un id d'ordi: ");
		String nomRecupered = vue.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
		if( ! nomRecupered.equals(""))
			ComputerDAO.getInstance().updateComputer(id, nomRecupered);
		Date dateRecupered = vue.getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRecupered != null){
			ComputerDAO.getInstance().updateComputer(id, dateRecupered);
		}
	}
	
	public void deleteComputer(){
		int id2 = vue.getIntFromConsole("Veuillez entrer un id d'ordi: ");
		ComputerDAO.getInstance().deleteComputer(id2);
	}
	
	
	
	public boolean checkString(String motif, String chaine){
		if(chaine == null){
			return false;
		}
		Pattern pattern = Pattern.compile(motif);
        Matcher matcher = pattern.matcher(chaine);
        if(matcher.matches()) {
            return true;
        }
        return false;
	}
	
	public Date stringToDate(String strRecuperee){
		Date dateRetour = null;
		
		if(checkString(Constantes.REGEX_DATE, strRecuperee)){
			try {
				dateRetour = Constantes.dateFormat.parse(strRecuperee);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return dateRetour;
		}
		return null;
	}
	
	
// String xxx(message,messageError,regex)
}
