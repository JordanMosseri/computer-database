package com.excilys.computerdatabase.main;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.computerdatabase.dao.CompanyDAO;
import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Constantes;

public class Service {
	
	//TODO supprimer view
	public  View view;
	
	
	
	public List<Computer> getComputers(){
		return ComputerDAO.getInstance().getAll();
	}
	
	public Paging<ComputerDTO> getComputers(int offset, int limit){
		Paging<Computer> pagingComputer = ComputerDAO.getInstance().getAll(offset, limit);
		List<ComputerDTO> listComputerDTO = new ArrayList<ComputerDTO>();
		for (Computer computer : pagingComputer.actualList) {
			listComputerDTO.add(DTOMapper.convert(computer));
		}
		return new Paging<ComputerDTO>(pagingComputer.offset, listComputerDTO, pagingComputer.indexPage, pagingComputer.totalSize);
	}
	
	public List<Company> getCompanies(){
		return CompanyDAO.getInstance().getListCompanies();
	}
	
	public Computer showComputerDetails(int id){
		return ComputerDAO.getInstance().getComputer(id);
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param comp
	 * @return
	 */
	public boolean addComputer(Computer comp){
		
		//Company Id is provided
		if(comp.company.id >= 0){
			
			//Company Id exists in db
			if(CompanyDAO.getInstance().companyExists(comp.company.id)){
				//OK
			}
			
			//Wrong company id
			else{
				throw new IllegalStateException("Inserting computer : companyId >= 0 but doesn't exists in database.");
			}
		}
		
		//Company Name is provided
		else if( comp.company.name != null && !comp.company.name.trim().isEmpty() ){
			
			//Company Name exists in db
			comp.company.id = CompanyDAO.getInstance().getCompanyIdIfNameExists(comp.company.name);
			
			if(comp.company.id<0){
				comp.company.id = CompanyDAO.getInstance().insertCompany(comp.company.name);
			}
		}
		else{
			throw new IllegalStateException("Inserting computer : companyId < 0 and companyName not entered.");
		}
		
		
		if (!CompanyDAO.getInstance().companyExists(comp.company.id)) {
			throw new IllegalStateException("Inserting computer : companyId doesn't exists in database.");
		}
		
		return ComputerDAO.getInstance().insertComputer(comp);
	}
	
	public void updateComputer(){
		if(view != null){
			int id = view.getIntFromConsole("Veuillez entrer un id d'ordi: ");
			String nomRecupered = view.getStringFromConsole("Veuillez entrer un nouveau nom d'ordi (vide pour passer): ");
			if( ! nomRecupered.equals(""))
				ComputerDAO.getInstance().updateComputer(id, nomRecupered);
			LocalDateTime dateRecupered = view.getDateFromConsole("Veuillez entrer une date d'ajout au format "+Constantes.FORMAT_DATE+" (vide pour passer): ");
			if(dateRecupered != null){
				//TODO LocalDateTime
				//ComputerDAO.getInstance().updateComputer(id, dateRecupered);
			}
		}
	}
	
	public boolean updateComputer(Computer c){
		return ComputerDAO.getInstance().updateComputer(c);
	}
	
	public boolean deleteComputer(int id2){
		return ComputerDAO.getInstance().deleteComputer(id2);
	}
	
	public ComputerDTO getComputer(int id){
		return DTOMapper.convert(ComputerDAO.getInstance().getComputer(id));
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
	
	public static LocalDateTime parse(String strRecuperee){
		if(strRecuperee==null || !checkString(Constantes.REGEX_DATE, strRecuperee)) {
			return null;
		}
		//TODO faire avec time dans pattern direct?
		return LocalDate.parse(strRecuperee, DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE)).atTime(0, 0);
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
