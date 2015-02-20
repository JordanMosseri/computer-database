package main;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import dao.ComputerDAO;
import modele.*;



public class MainClass {
	
	static ComputerDAO computerdao = new ComputerDAO();
	static Scanner in = new Scanner(System.in);
	
	static String[] possibilites = {
			"List computers", 
			"List companies", 
			"Show computer details (the detailed information of only one computer)", 
			"Create a computer", 
			"Update a computer", 
			"Delete a computer",
			"Quitter"
			};
	
	public static void main(String[] args) {
		
		boolean continuer = true;
		
		while(continuer){
			int selection = afficherMenu();
			if(selection>=1 && selection<=possibilites.length)
				System.out.println(possibilites[selection-1].toUpperCase());
			switch (selection) {
			case 1:
				listerComputers();
				break;
			case 2:
				listerCompanies();
				break;
			case 3:
				Computer c = computerdao.getComputer(recupIntFromConsole("Veuillez entrer un id d'ordi: "));
				System.out.println(c);
				break;
			case 4:
				computerdao.insereComputer(recupOrdiFromConsole());
				listerComputers();
				break;
			case 5:
				int id = recupIntFromConsole("Veuillez entrer un id d'ordi: ");
				String nomRecupered = recupStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
				if( ! nomRecupered.equals(""))
					computerdao.updateComputer(id, nomRecupered);
				Date dateRecupered = getDateFromConsole();
				if(dateRecupered != null){
					computerdao.updateComputer(id, dateRecupered);
				}
				
				break;
			case 6:
				int id2 = recupIntFromConsole("Veuillez entrer un id d'ordi: ");
				computerdao.deleteComputer(id2);
				break;
			case 7:
				continuer=false;
				System.out.println("Au revoir...");
				break;
			default:
				break;
			}
		}
		
		
		
		
		
	}
	
	public static void listerComputers(){
		List<Computer> p = computerdao.getListComputers();
		System.out.println(p);
	}
	
	public static void listerCompanies(){
		List<Fabriquant> p = computerdao.companyDAO.getListCompanies();
		System.out.println(p);
	}
	
	public static int afficherMenu(){
		
		int selection=-1;
		
		while(selection<=0 || selection>=possibilites.length+1){
			
			System.out.println("\n---BIENVENUE---");
			
			for (int i = 0; i < possibilites.length; i++) {
				System.out.println((i+1)+"-"+possibilites[i]);
			}
			
			selection = recupIntFromConsole("Veuillez selectionner une option: ");
		}
		
		//System.out.println("Vous avez selectionne "+selection);
		return selection;
	}
	
	public static Computer recupOrdiFromConsole(){
		String nom = recupStringFromConsole("Veuillez entrer un nom d'ordi: ");
		String fab = recupStringFromConsole("Veuillez entrer un nom de fabriquant: ");
		Date d = getDateFromConsole();
		//Date d = new Date(System.currentTimeMillis());
		System.out.println();
		
		//System.out.println(nom+" "+fab);
		return new Computer(nom,d,new Fabriquant(fab));
	}
	
	
	
	
	public static int recupIntFromConsole(String message){
		int id=-1;
		
		while(id<=-1){
			System.out.print(message);
			try{
				id = in.nextInt();
			}
			catch(java.util.InputMismatchException e){ }
		}
		
		in.nextLine();
		System.out.println();

		return id;
	}
	
	public static String recupStringFromConsole(String message){
		//in.nextLine();
		String nom;
		System.out.print(message);
		nom = in.nextLine();
		return nom;
	}
	
	public static Date getDateFromConsole(){
		String strRecuperee="";
		boolean ok=false;
		while(!ok){
			strRecuperee = recupStringFromConsole("Veuillez entrer une date au format XXX: ");
			if(strRecuperee.equals("")){
				return null;
			}
			
			//VERIF
			if(strRecuperee.contains("-")){
				String[] temp = strRecuperee.split("-");
				if(temp.length ==3){
					/*for (String part : temp) {
						if(que des chiffres)
					}*/
					ok=true;
				}
			}
			
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date dateRetour = null;
		try {
			dateRetour = dateFormat.parse(strRecuperee);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateRetour;
	}
// String xxx(message,messageError,regex)
}
