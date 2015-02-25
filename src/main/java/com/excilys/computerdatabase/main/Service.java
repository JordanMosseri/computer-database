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
import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Constantes;

public class Service {
	
	//TODO supprimer view
	public  View view;
	
	
	
	public List<Computer> getComputers(){
		return ComputerDAO.getInstance().getAll();
	}
	
	public List<Company> getCompanies(){
		return CompanyDAO.getInstance().getListCompanies();
	}
	
	public Computer showComputerDetails(int id){
		return ComputerDAO.getInstance().getComputer(id);
	}
	
	public boolean addComputer(Computer c){
		return ComputerDAO.getInstance().insereComputer(c);
	}
	
	public void updateComputer(){
		if(view != null){
			int id = view.getIntFromConsole("Veuillez entrer un id d'ordi: ");
			String nomRecupered = view.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
			if( ! nomRecupered.equals(""))
				ComputerDAO.getInstance().updateComputer(id, nomRecupered);
			Date dateRecupered = view.getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
			if(dateRecupered != null){
				ComputerDAO.getInstance().updateComputer(id, dateRecupered);
			}
		}
		
	}
	
	public boolean deleteComputer(int id2){
		return ComputerDAO.getInstance().deleteComputer(id2);
	}
	
	
	
	public static boolean checkString(String motif, String chaine){
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
	
	public static Date stringToDate(String strRecuperee){
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
	
	public static int stringToInt(String strRecuperee){
		int id = -1;
		if (Service.checkString(Constantes.REGEX_INTEGER, strRecuperee)) {
			try{
				//id = in.nextInt();
				id = Integer.parseInt(strRecuperee);
			}
			catch(Exception e){ }//java.util.InputMismatchException
		}
		return id;
	}
	
	
// String xxx(message,messageError,regex)
}
