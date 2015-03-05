package com.excilys.computerdatabase.ui.cli;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.util.Constantes;

public class View {
	
	public static final String[] POSSIBILITES = {
		"List computers", 
		"List companies", 
		"Show computer details (the detailed information of only one computer)", 
		"Create a computer", 
		"Update a computer", 
		"Delete a computer",
		"Delete a company",
		"Quitter"
		};
	
	Scanner in = new Scanner(System.in);
	public Service service = new Service();
	
	public  void lancerProgramme() {
		
		boolean continuer = true;
		
		while(continuer){
			
			int selection = this.showMenuAndGetChoice();
			
			
			switch (selection) {
			case 1:
				this.println( service.getComputers() );
				break;
			case 2:
				this.println( service.getCompanies() );
				break;
			case 3:
				this.println( service.getComputer( this.getIntFromConsole("Please enter a computer id: ") ) );
				break;
			case 4:
				println(service.addComputer( DTOMapper.convert(this.getComputerDTOFromConsole()) ));
				break;
			case 5:
				int id = this.getIntFromConsole("Please enter a computer id: ");
				
				ComputerDTO computerDTO = DTOMapper.convert(service.getComputer(id));
				
				String nomRecupered = this.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
				if(!nomRecupered.equals("")){
					computerDTO.name = nomRecupered;
				}
				
				String dateRecupered = this.getDateFromConsole("Please enter an added date formatted "+Constantes.FORMAT_DATE+" (leave empty to skip): ");
				if(dateRecupered != null){
					computerDTO.dateAdded = dateRecupered;
				}
				
				String dateRemovedRecupered = this.getDateFromConsole("Please enter a removed date formatted "+Constantes.FORMAT_DATE+" (leave empty to skip): ");
				if(dateRemovedRecupered != null){
					computerDTO.dateRemoved = dateRemovedRecupered;
				}
				
				println(service.updateComputer(DTOMapper.convert(computerDTO)));
				break;
			case 6:
				println(service.deleteComputer( this.getIntFromConsole("Please enter a computer id: ") ));
				break;
			case 7:
				println(service.deleteCompany(getIntFromConsole("Please enter a computer id: ")));
				break;
			case 8:
				continuer=false;
				this.println("Goodbye...");
				break;
			default:
				break;
			}
		}
		
	}
	
	public  int showMenuAndGetChoice(){
		
		int selection=-1;
		
		while(selection<=0 || selection>=POSSIBILITES.length+1){
			
			this.println("\n---WELCOME---");
			
			for (int i = 0; i < POSSIBILITES.length; i++) {
				this.println((i+1)+"-"+POSSIBILITES[i]);
			}
			
			selection = getIntFromConsole("Please type a number: ");
		}
		
		if(selection>=1 && selection<=POSSIBILITES.length)
			this.println(POSSIBILITES[selection-1].toUpperCase());
		
		//this.println("Vous avez selectionne "+selection);
		return selection;
	}
	
	public  int getIntFromConsole(String message){
		int id=-1;
		boolean ok=false;
		
		while(!ok){
			this.print(message);
			
			String strRecuperee = in.nextLine();
			id = NumberUtils.toInt(strRecuperee);
			if (id >= 0){
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
	
	public  ComputerDTO getComputerDTOFromConsole(){
		String nom = getStringFromConsole("Please enter a computer name: ");
		String fab = getStringFromConsole("Please enter a company name: ");
		String dateAddedEntered = getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		String dateRemovedEntered = getDateFromConsole("Veuillez entrer une date de suppression au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
		//Date d = new Date(System.currentTimeMillis());
		this.println();
		
		//this.println(nom+" "+fab);
		return new ComputerDTO(-1, nom,dateAddedEntered, dateRemovedEntered,new Company(fab));
	}
	
	
	
	public  String getDateFromConsole(String message){
		String strRecuperee="";
		boolean ok=false;
		while(!ok){
			strRecuperee = getStringFromConsole(message);
			if(strRecuperee.equals("")){
				return null;
			}
			
			//Check if the string entered is formatted like a date
			ok = Service.checkString(Constantes.REGEX_DATE, strRecuperee);
			
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
		
		
		return strRecuperee;
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
