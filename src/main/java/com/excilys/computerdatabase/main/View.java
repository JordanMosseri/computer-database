package com.excilys.computerdatabase.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.util.Constantes;

public class View {
	
	Scanner in = new Scanner(System.in);
	Service controleur;
	
	
	
	public  int showMenuAndGetChoice(){
		
		int selection=-1;
		
		while(selection<=0 || selection>=Constantes.POSSIBILITES.length+1){
			
			this.println("\n---BIENVENUE---");
			
			for (int i = 0; i < Constantes.POSSIBILITES.length; i++) {
				this.println((i+1)+"-"+Constantes.POSSIBILITES[i]);
			}
			
			selection = getIntFromConsole("Veuillez selectionner une option: ");
		}
		
		if(selection>=1 && selection<=Constantes.POSSIBILITES.length)
			this.println(Constantes.POSSIBILITES[selection-1].toUpperCase());
		
		//this.println("Vous avez selectionne "+selection);
		return selection;
	}
	
	public  int getIntFromConsole(String message){
		int id=-1;
		boolean ok=false;
		
		while(!ok){
			this.print(message);
			
			String strRecuperee = in.nextLine();
			if (controleur.checkString(Constantes.REGEX_INTEGER, strRecuperee)) {
				try{
					//id = in.nextInt();
					id = Integer.parseInt(strRecuperee);
				}
				catch(Exception e){ }//java.util.InputMismatchException
				ok=true;
			}
		}
		
		//in.nextLine();
		this.println();

		return id;
	}
	
	public  String getStringFromConsole(String message){
		//in.nextLine();
		String nom;
		this.print(message);
		nom = in.nextLine();
		return nom;
	}
	
	public  Computer getComputerFromConsole(){
		String nom = getStringFromConsole("Veuillez entrer un nom d'ordi: ");
		String fab = getStringFromConsole("Veuillez entrer un nom de fabriquant: ");
		Date dateRecupered = getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRecupered == null){
			dateRecupered=new Date();
		}
		Date dateRemovedRecupered = getDateFromConsole("Veuillez entrer une date de suppression au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		if(dateRemovedRecupered == null){
			dateRemovedRecupered=new Date();
		}
		//Date d = new Date(System.currentTimeMillis());
		this.println();
		
		//this.println(nom+" "+fab);
		return new Computer(-1, nom,dateRecupered, dateRemovedRecupered,new Company(fab));
	}
	
	
	
	public  Date getDateFromConsole(String message){
		String strRecuperee="";
		boolean ok=false;
		while(!ok){
			strRecuperee = getStringFromConsole(message);
			if(strRecuperee.equals("")){
				return null;
			}
			
			//VERIF
			ok = controleur.checkString(Constantes.REGEX_DATE, strRecuperee);
			
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
		
		
		return controleur.stringToDate(strRecuperee);
	}
	
	
	public void print(Object o){
		System.out.print(o.toString());
        //log(o);
	}
	
	public void println(Object o){
		this.print(o.toString());
		this.println();
	}
	
	public void println(){
		System.out.println();
	}
}
