package com.excilys.computerdatabase.cli;

import java.util.ArrayList;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.math.NumberUtils;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.util.LocaleUtils;
import com.excilys.computerdatabase.util.Utils;

/**
 * Implementation of {@link IView}
 * @author Jordan Mosseri
 *
 */
@Component
public class View implements IView {
	
	/**
	 * Functionalities to display in the menu
	 */
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
	
	/**
	 * Used to get user's typing
	 */
	Scanner in = new Scanner(System.in);
	
	/**
	 * Computer webservice's URL
	 */
	public final static String computerWebserviceURL = "http://localhost:8080/webservice/rest/computerWebService";
	
	/**
	 * Company webservice's URL
	 */
	public final static String companyWebserviceURL = "http://localhost:8080/webservice/rest/companyWebService";
	
	/**
	 * Web service client
	 */
	Client wsClient;
	
	/**
	 * Computer webservice target to execute functions and get the results
	 */
	WebTarget computerTarget;
	
	/**
	 * Company webservice target to execute functions and get the results
	 */
	WebTarget companyTarget;
	
	@Override
	public void initWebservice() {
		wsClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
		computerTarget = wsClient.target(computerWebserviceURL);
		companyTarget = wsClient.target(companyWebserviceURL);
	}
	
	@Override
	public  void runProgram() {
		
		initWebservice();
		
		boolean continuer = true;
		
		while(continuer){
			
			int selection = this.showMenuAndGetChoice();
			
			
			switch (selection) {
			case 1:
				this.println(
						computerTarget.path("/getAll").request(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<ComputerDTO>>() {})
						);
				break;
			case 2:
				this.println(
						companyTarget.path("/getAll").request(MediaType.APPLICATION_JSON).get(new GenericType<ArrayList<Company>>() {})
						);
				break;
			case 3:
				int idToGet = this.getIntFromConsole("Please enter a computer id: ");
				this.println(
						computerTarget.path("/get/"+idToGet).request(MediaType.APPLICATION_JSON).get(new GenericType<ComputerDTO>() {})
						);
				break;
			case 4:
				
				Response responseAdd = computerTarget.path("/add").request(MediaType.APPLICATION_JSON).post(Entity.entity(this.getComputerDTOFromConsole(), MediaType.APPLICATION_JSON));
				if(responseAdd.getStatus() != 200 && responseAdd.getStatus() != 204 ) {
					System.out.println("Error http " + responseAdd.getStatus());
				}
				
				println(responseAdd.getEntity());
				break;
			case 5:
				int id = this.getIntFromConsole("Please enter a computer id: ");
				
				ComputerDTO computerDTO = computerTarget.path("/get/"+id).request(MediaType.APPLICATION_JSON).get(new GenericType<ComputerDTO>() {});
				
				String nomRecupered = this.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
				if(!nomRecupered.equals("")){
					computerDTO.setName(nomRecupered);
				}
				
				String dateRecupered = this.getDateFromConsole("Please enter an added date formatted "+LocaleUtils.getPatternOfCurrentLocale()+" (leave empty to skip): ");
				if(dateRecupered != null){
					computerDTO.setDateAdded(dateRecupered);
				}
				
				String dateRemovedRecupered = this.getDateFromConsole("Please enter a removed date formatted "+LocaleUtils.getPatternOfCurrentLocale()+" (leave empty to skip): ");
				if(dateRemovedRecupered != null){
					computerDTO.setDateRemoved(dateRemovedRecupered);
				}
				
				Response responseUpdate = computerTarget.path("/update").request(MediaType.APPLICATION_JSON).post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
				if(responseUpdate.getStatus() != 200 && responseUpdate.getStatus() != 204 ) {
					System.out.println("Error http " + responseUpdate.getStatus());
				}
				
				println(responseUpdate.getEntity());
				break;
			case 6:
				int idToDelete = this.getIntFromConsole("Please enter a computer id: ");
				
				Response responseDelete = computerTarget.path("/delete/" + idToDelete).request(MediaType.APPLICATION_JSON).delete();
				if(responseDelete.getStatus() != 200 && responseDelete.getStatus() != 204 ) {
					System.out.println("Error http " + responseDelete.getStatus());
				}
				
				println(responseDelete.getEntity());
				break;
			case 7:
				int idToDeleteCompany = getIntFromConsole("Please enter a computer id: ");
				
				Response responseDeleteCompany = companyTarget.path("/delete/" + idToDeleteCompany).request(MediaType.APPLICATION_JSON).delete();
				if(responseDeleteCompany.getStatus() != 200 && responseDeleteCompany.getStatus() != 204 ) {
					System.out.println("Error http " + responseDeleteCompany.getStatus());
				}
				
				println(responseDeleteCompany.getEntity());
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
		String dateAddedEntered = getDateFromConsole("Veuillez entrer une date d'ajout au format "+LocaleUtils.getPatternOfCurrentLocale()+" (vide pour passer): ");
		String dateRemovedEntered = getDateFromConsole("Veuillez entrer une date de suppression au format "+LocaleUtils.getPatternOfCurrentLocale()+" (vide pour passer): ");
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
