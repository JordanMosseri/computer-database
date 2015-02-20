package main;
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
			switch (selection) {
			case 1:
				listerComputers();
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:
				computerdao.insereComputer(recupOrdiFromConsole());
				listerComputers();
				break;
			case 5:

				break;
			case 6:

				break;
			case 7:
				continuer=false;
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
	
	public static int afficherMenu(){
		
		int selection=-1;
		
		while(selection<=0 || selection>=possibilites.length+1){
			
			System.out.println("\n---BIENVENUE---");
			
			for (int i = 0; i < possibilites.length; i++) {
				System.out.println((i+1)+"-"+possibilites[i]);
			}
			
			System.out.print("Veuillez selectionner une option: ");
			selection = in.nextInt();
			System.out.println();
		}
		
		//System.out.println("Vous avez selectionne "+selection);
		return selection;
	}
	
	public static Computer recupOrdiFromConsole(){
		String nom = null, fab=null;
		
		in.nextLine();
		
		while(nom==null){
			System.out.print("Veuillez entrer un nom d'ordi: ");
			nom = in.nextLine();
		}
		while(fab==null){
			System.out.print("Veuillez entrer un nom de fabriquant: ");
			fab = in.nextLine();
		}
		System.out.println();
		
		//System.out.println(nom+" "+fab);
		//System.out.println("-"+fab+"-");
		
		return new Computer(nom,new Date(System.currentTimeMillis()),new Fabriquant(fab));
	}

}
