package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import modele.Computer;
import modele.Company;

public class View {
	
	Scanner in = new Scanner(System.in);
	Service controleur;
	
	public  int afficherMenu(){
		
		int selection=-1;
		
		while(selection<=0 || selection>=Constantes.POSSIBILITES.length+1){
			
			System.out.println("\n---BIENVENUE---");
			
			for (int i = 0; i < Constantes.POSSIBILITES.length; i++) {
				System.out.println((i+1)+"-"+Constantes.POSSIBILITES[i]);
			}
			
			selection = recupIntFromConsole("Veuillez selectionner une option: ");
		}
		
		if(selection>=1 && selection<=Constantes.POSSIBILITES.length)
			this.println(Constantes.POSSIBILITES[selection-1].toUpperCase());
		
		//System.out.println("Vous avez selectionne "+selection);
		return selection;
	}
	
	public  int recupIntFromConsole(String message){
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
	
	public  String recupStringFromConsole(String message){
		//in.nextLine();
		String nom;
		System.out.print(message);
		nom = in.nextLine();
		return nom;
	}
	
	public  Computer recupOrdiFromConsole(){
		String nom = recupStringFromConsole("Veuillez entrer un nom d'ordi: ");
		String fab = recupStringFromConsole("Veuillez entrer un nom de fabriquant: ");
		Date dateRecupered = getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRecupered == null){
			dateRecupered=new Date();
		}
		Date dateRemovedRecupered = getDateFromConsole("Veuillez entrer une date de suppression au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRemovedRecupered == null){
			dateRemovedRecupered=new Date();
		}
		//Date d = new Date(System.currentTimeMillis());
		System.out.println();
		
		//System.out.println(nom+" "+fab);
		return new Computer(nom,dateRecupered, dateRemovedRecupered,new Company(fab));
	}
	
	
	
	public  Date getDateFromConsole(String message){
		String strRecuperee="";
		boolean ok=false;
		while(!ok){
			strRecuperee = recupStringFromConsole(message);
			if(strRecuperee.equals("")){
				return null;
			}
			
			//VERIF
			ok = controleur.verifierChaine(Constantes.MOTIF, strRecuperee);
			
			/*if(strRecuperee.contains("-")){
				String[] temp = strRecuperee.split("-");
				if(temp.length ==3){
					//for (String part : temp) {
						//if(que des chiffres)
					//}
					ok=true;
				}
			}*/
			
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.FORMAT_DATE);
		
		Date dateRetour = null;
		try {
			dateRetour = dateFormat.parse(strRecuperee);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateRetour;
	}
	
	
	public void print(Object o){
		System.out.print(o.toString());
	}
	public void println(Object o){
		System.out.println(o.toString());
	}
}
