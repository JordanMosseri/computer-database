package com.excilys.computerdatabase.service;
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

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistance.AbstractDAO;
import com.excilys.computerdatabase.persistance.CompanyDAO;
import com.excilys.computerdatabase.persistance.ComputerDAO;
import com.excilys.computerdatabase.ui.cli.View;
import com.excilys.computerdatabase.util.Constantes;

public class Service {
	
	
	public List<Computer> getComputers(){
		Connection cn = AbstractDAO.getConnexion();
		
		List<Computer> computers = ComputerDAO.getInstance().getAll("", cn);
		
		tryClose(cn);
		
		return computers;
	}
	
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		Connection cn = AbstractDAO.getConnexion();
		
		//Get part of computers
		List<Computer> partOfComputers = ComputerDAO.getInstance().getPart(offset, limit, word, cn);
		
		//Returns Paging object
		Paging<ComputerDTO> page = new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, ComputerDAO.getInstance().getTotalCount(cn));
		
		tryClose(cn);
		
		return page;
	}
	
	public List<Company> getCompanies(){
		Connection cn = AbstractDAO.getConnexion();
		
		List<Company> companies = CompanyDAO.getInstance().getAll(cn);
		
		tryClose(cn);
		
		return companies;
	}
	
	public Computer getComputer(int id){
		Connection cn = AbstractDAO.getConnexion();
		
		Computer computer = ComputerDAO.getInstance().get(id, cn);
		
		tryClose(cn);
		
		return computer;
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param comp
	 * @return
	 */
	public boolean addComputer(Computer comp){
		Connection cn = AbstractDAO.getConnexion();
		
		//Company Id is provided
		if(comp.company.id >= 0){
			
			//Company Id exists in db
			if(CompanyDAO.getInstance().exists(comp.company.id, cn)){
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
			comp.company.id = CompanyDAO.getInstance().getIdIfNameExists(comp.company.name, cn);
			
			if(comp.company.id<0){
				comp.company.id = CompanyDAO.getInstance().insert(comp.company.name, cn);
			}
		}
		else{
			throw new IllegalStateException("Inserting computer : companyId < 0 and companyName not entered.");
		}
		
		
		if (!CompanyDAO.getInstance().exists(comp.company.id, cn)) {
			throw new IllegalStateException("Inserting computer : companyId doesn't exists in database.");
		}
		
		boolean result =  ComputerDAO.getInstance().insert(comp, cn);
		
		tryClose(cn);
		
		return result;
	}
	
	
	public boolean updateComputer(Computer c){
		Connection cn = AbstractDAO.getConnexion();
		
		boolean res = ComputerDAO.getInstance().update(c, cn);
		
		tryClose(cn);
		
		return res;
	}
	
	public boolean deleteComputer(int id){
		Connection cn = AbstractDAO.getConnexion();
		
		boolean res = ComputerDAO.getInstance().delete(id, cn);
		
		tryClose(cn);
		
		return res;
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
		finally{
			try {
				cn.setAutoCommit(true);
				
				//Release connection to the pool (do not close it)
				if (cn != null)
					cn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean computerExists(int id){
		Connection cn = AbstractDAO.getConnexion();
		
		boolean res = ComputerDAO.getInstance().exists(id, cn);
		
		tryClose(cn);
		
		return res;
	}
	
	public void tryClose(Connection cn){
		try {
			if (cn != null)
				cn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
