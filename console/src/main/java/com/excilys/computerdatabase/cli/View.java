package com.excilys.computerdatabase.cli;

import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.ICompanyService;
import com.excilys.computerdatabase.service.IComputerService;
import com.excilys.computerdatabase.util.Utils;

@Component
public class View implements IView {
	
	@Autowired
	IComputerService computerService;
	
	@Autowired
	ICompanyService companyService;
	
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
	
	
	@Override
	public  void lancerProgramme() {
		
		boolean continuer = true;
		
		while(continuer){
			
			int selection = this.showMenuAndGetChoice();
			
			
			switch (selection) {
			case 1:
				this.println( computerService.getComputers() );
				break;
			case 2:
				this.println( companyService.getCompanies() );
				break;
			case 3:
				this.println( computerService.getComputer( this.getIntFromConsole("Please enter a computer id: ") ) );
				break;
			case 4:
				println(computerService.addComputer( DTOMapper.convert(this.getComputerDTOFromConsole()) ));
				break;
			case 5:
				int id = this.getIntFromConsole("Please enter a computer id: ");
				
				ComputerDTO computerDTO = DTOMapper.convert(computerService.getComputer(id));
				
				String nomRecupered = this.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
				if(!nomRecupered.equals("")){
					computerDTO.setName(nomRecupered);
				}
				
				String dateRecupered = this.getDateFromConsole("Please enter an added date formatted "+Utils.getPatternOfCurrentLocale()+" (leave empty to skip): ");
				if(dateRecupered != null){
					computerDTO.setDateAdded(dateRecupered);
				}
				
				String dateRemovedRecupered = this.getDateFromConsole("Please enter a removed date formatted "+Utils.getPatternOfCurrentLocale()+" (leave empty to skip): ");
				if(dateRemovedRecupered != null){
					computerDTO.setDateRemoved(dateRemovedRecupered);
				}
				
				println(computerService.updateComputer(DTOMapper.convert(computerDTO)));
				break;
			case 6:
				println(computerService.deleteComputer( this.getIntFromConsole("Please enter a computer id: ") ));
				break;
			case 7:
				println(companyService.deleteCompany(getIntFromConsole("Please enter a computer id: ")));
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
	
	@Override
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
		
		return selection;
	}
	
	@Override
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
	
	@Override
	public  String getStringFromConsole(String message){
		//in.nextLine();
		String nom;
		this.print(message);
		nom = in.nextLine();
		return nom;
	}
	
	@Override
	public  ComputerDTO getComputerDTOFromConsole(){
		String nom = getStringFromConsole("Please enter a computer name: ");
		String fab = getStringFromConsole("Please enter a company name: ");
		String dateAddedEntered = getDateFromConsole("Veuillez entrer une date d'ajout au format "+Utils.getPatternOfCurrentLocale()+" (vide pour passer): ");
		String dateRemovedEntered = getDateFromConsole("Veuillez entrer une date de suppression au format "+Utils.getPatternOfCurrentLocale()+" (vide pour passer): ");
		//Date d = new Date(System.currentTimeMillis());
		this.println();
		
		return new ComputerDTO(-1, nom,dateAddedEntered, dateRemovedEntered,new Company(fab));
	}
	
	
	
	@Override
	public  String getDateFromConsole(String message){
		String strRecuperee="";
		boolean ok=false;
		while(!ok){
			strRecuperee = getStringFromConsole(message);
			if(strRecuperee.equals("")){
				return null;
			}
			
			//Check if the string entered is formatted like a date
			ok = Utils.isDate(strRecuperee);
		}
		
		
		return strRecuperee;
	}
	
	
	
	// String xxx(message,messageError,regex)
	
	@Override
	public void print(Object o){
		System.out.print(o.toString());
        //log(o);
	}
	
	@Override
	public void println(Object o){
		this.print(o.toString());
		this.println();
	}
	
	@Override
	public void println(){
		System.out.println();
	}
}
