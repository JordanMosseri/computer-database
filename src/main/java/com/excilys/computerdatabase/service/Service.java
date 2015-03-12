package com.excilys.computerdatabase.service;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistance.DAOUtils;
import com.excilys.computerdatabase.persistance.CompanyDAO;
import com.excilys.computerdatabase.persistance.ComputerDAO;
import com.excilys.computerdatabase.persistance.ICompanyDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.util.Constantes;

@org.springframework.stereotype.Service
public class Service implements IService {
	
	@Autowired
	//@Qualifier(value = "ComputerDAO")
	IComputerDAO computerDAO;
	
	@Autowired
	//@Qualifier(value = "CompanyDAO")
	ICompanyDAO companyDAO;
	
	@Override
	public List<Computer> getComputers(){
		Connection cn = DAOUtils.getConnexion();
		
		//System.out.println(""+(computerDAO==null)+" "+(cn==null));
		
		List<Computer> computers = computerDAO.getAll("", cn);
		
		tryClose(cn);
		
		return computers;
	}
	
	@Override
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		Connection cn = DAOUtils.getConnexion();
		
		//Get part of computers
		List<Computer> partOfComputers = computerDAO.getPart(offset, limit, word, cn);
		
		//Returns Paging object
		Paging<ComputerDTO> page = new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, computerDAO.getTotalCount(cn));
		
		tryClose(cn);
		
		return page;
	}
	
	@Override
	public List<Company> getCompanies(){
		Connection cn = DAOUtils.getConnexion();
		
		List<Company> companies = companyDAO.getAll(cn);
		
		tryClose(cn);
		
		return companies;
	}
	
	@Override
	public Computer getComputer(int id){
		Connection cn = DAOUtils.getConnexion();
		
		Computer computer = computerDAO.get(id, cn);
		
		tryClose(cn);
		
		return computer;
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param comp
	 * @return
	 */
	@Override
	public boolean addComputer(Computer comp){
		Connection cn = DAOUtils.getConnexion();
		
		//Company Id is provided
		if(comp.company.id >= 0){
			
			//Company Id exists in db
			if(companyDAO.exists(comp.company.id, cn)){
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
			comp.company.id = companyDAO.getIdIfNameExists(comp.company.name, cn);
			
			if(comp.company.id<0){
				comp.company.id = companyDAO.insert(comp.company.name, cn);
			}
		}
		else{
			throw new IllegalStateException("Inserting computer : companyId < 0 and companyName not entered.");
		}
		
		
		if (!companyDAO.exists(comp.company.id, cn)) {
			throw new IllegalStateException("Inserting computer : companyId doesn't exists in database.");
		}
		
		boolean result =  computerDAO.insert(comp, cn);
		
		tryClose(cn);
		
		return result;
	}
	
	
	@Override
	public boolean updateComputer(Computer c){
		Connection cn = DAOUtils.getConnexion();
		
		boolean res = computerDAO.update(c, cn);
		
		tryClose(cn);
		
		return res;
	}
	
	@Override
	public boolean deleteComputer(int id){
		Connection cn = DAOUtils.getConnexion();
		
		boolean res = computerDAO.delete(id, cn);
		
		tryClose(cn);
		
		return res;
	}
	
	@Override
	public boolean deleteCompany(int id){
		//Get a connection for the transaction
		Connection cn = DAOUtils.getConnexion();
		
		try {
			cn.setAutoCommit(false);
			
			//Delete computers linked to the company first (avoid exception due to constraint key)
			boolean ok1 = computerDAO.deleteThoseFromCompany(id, cn);
			
			//Delete company
			boolean ok2 = companyDAO.delete(id, cn);
			
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
	
	@Override
	public boolean computerExists(int id){
		Connection cn = DAOUtils.getConnexion();
		
		boolean res = computerDAO.exists(id, cn);
		
		tryClose(cn);
		
		return res;
	}
	
	private void tryClose(Connection cn){
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
		computers.addAll(ComputerDAO.INSTANCE.search(word));
		
		//Get ids from the search among companies
		List<Integer> listInt = CompanyDAO.INSTANCE.search(word);
		
		//For each company
		for (Integer integer : listInt) {
			//Get computers linked to this company
			computers.addAll(ComputerDAO.INSTANCE.getThoseFromCompany(integer));
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
