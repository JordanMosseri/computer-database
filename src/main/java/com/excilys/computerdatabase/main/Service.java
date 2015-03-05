package com.excilys.computerdatabase.main;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.computerdatabase.dao.AbstractDAO;
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
	
	
	public List<Computer> getComputers(){
		return ComputerDAO.getInstance().getAll("", AbstractDAO.getConnexion());
	}
	
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		//Get part of computers
		List<Computer> partOfComputers = ComputerDAO.getInstance().getPart(offset, limit, word, AbstractDAO.getConnexion());
		
		//Returns Paging object
		return new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, ComputerDAO.getInstance().getTotalCount(AbstractDAO.getConnexion()));
	}
	
	public List<Company> getCompanies(){
		return CompanyDAO.getInstance().getAll(AbstractDAO.getConnexion());
	}
	
	public Computer getComputer(int id){
		return ComputerDAO.getInstance().get(id, AbstractDAO.getConnexion());
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
			if(CompanyDAO.getInstance().exists(comp.company.id, AbstractDAO.getConnexion())){
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
			comp.company.id = CompanyDAO.getInstance().getIdIfNameExists(comp.company.name, AbstractDAO.getConnexion());
			
			if(comp.company.id<0){
				comp.company.id = CompanyDAO.getInstance().insert(comp.company.name, AbstractDAO.getConnexion());
			}
		}
		else{
			throw new IllegalStateException("Inserting computer : companyId < 0 and companyName not entered.");
		}
		
		
		if (!CompanyDAO.getInstance().exists(comp.company.id, AbstractDAO.getConnexion())) {
			throw new IllegalStateException("Inserting computer : companyId doesn't exists in database.");
		}
		
		return ComputerDAO.getInstance().insert(comp, AbstractDAO.getConnexion());
	}
	
	
	public boolean updateComputer(Computer c){
		return ComputerDAO.getInstance().update(c, AbstractDAO.getConnexion());
	}
	
	public boolean deleteComputer(int id){
		return ComputerDAO.getInstance().delete(id, AbstractDAO.getConnexion());
	}
	
	public boolean deleteCompany(int id){
		//Get a connection for the transaction
		Connection cn = AbstractDAO.getConnexion();
		
		try {
			cn.setAutoCommit(false);
			
			//Delete computers linked to the company first (avoid exception due to constraint key)
			boolean ok1 = ComputerDAO.getInstance().deleteThoseFromCompany(id, cn);
			
			//Delete company
			boolean ok2 = CompanyDAO.getInstance().delete(id, cn);
			
			//Commit the transaction
			cn.commit();
			
			cn.setAutoCommit(true);
			
			return ok1 && ok2;
		} catch (SQLException e) {
			
			try {
				//Cancel the transaction because of an error
				cn.rollback();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean computerExists(int id){
		return ComputerDAO.getInstance().exists(id, AbstractDAO.getConnexion());
	}
	
	/*private List<ComputerDTO> search(String word){
		ArrayList<Computer> computers = new ArrayList<Computer>();
		
		//Search among computers
		computers.addAll(ComputerDAO.getInstance().search(word));
		
		//Get ids from the search among companies
		List<Integer> listInt = CompanyDAO.getInstance().search(word);
		
		//For each company
		for (Integer integer : listInt) {
			//Get computers linked to this company
			computers.addAll(ComputerDAO.getInstance().getThoseFromCompany(integer));
		}
		
		return DTOMapper.convert(computers);
	}*/
	
	
	
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
	
	public static LocalDateTime parse(String strRecuperee){
		if(strRecuperee==null || !checkString(Constantes.REGEX_DATE, strRecuperee)) {
			return null;
		}
		//TODO faire avec time dans pattern direct?
		return LocalDate.parse(strRecuperee, DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE)).atTime(0, 0);
	}
	
	
// String xxx(message,messageError,regex)
}
