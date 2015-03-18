package com.excilys.computerdatabase.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistance.ICompanyDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.util.Constantes;

@Service
public class ComputerService implements IComputerService {
	
	@Autowired
	IComputerDAO computerDAO;
	
	@Autowired
	ICompanyDAO companyDAO;
	
	@Override
	public List<Computer> getComputers(){
		
		//System.out.println(""+(computerDAO==null)+" "+(cn==null));
		
		return computerDAO.getAll("");
	}
	
	@Override
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		
		//Get part of computers
		List<Computer> partOfComputers = computerDAO.getPart(offset, limit, word);
		
		//Returns Paging object
		Paging<ComputerDTO> page = new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, computerDAO.getTotalCount());
		
		
		return page;
	}
	
	@Override
	public Computer getComputer(int id){
		
		return computerDAO.get(id);
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param comp
	 * @return
	 */
	@Override
	public boolean addComputer(Computer comp){
		
		
		//Company Id is provided
		if(comp.getCompany().getId() >= 0){
			
			//Company Id exists in db
			if(companyDAO.exists(comp.getCompany().getId())){
				//OK
			}
			
			//Wrong company id
			else{
				throw new IllegalStateException("Inserting computer : companyId >= 0 but doesn't exists in database.");
			}
		}
		
		//Company Name is provided
		else if( comp.getCompany().getName() != null && !comp.getCompany().getName().trim().isEmpty() ){
			
			//Company Name exists in db
			comp.getCompany().setId(companyDAO.getIdIfNameExists(comp.getCompany().getName()));
			
			if(comp.getCompany().getId()<0){
				comp.getCompany().setId(companyDAO.insert(comp.getCompany().getName()));
			}
		}
		else{
			throw new IllegalStateException("Inserting computer : companyId < 0 and companyName not entered.");
		}
		
		
		if (!companyDAO.exists(comp.getCompany().getId())) {
			throw new IllegalStateException("Inserting computer : companyId doesn't exists in database.");
		}
		
		return computerDAO.insert(comp);
	}
	
	
	@Override
	public boolean updateComputer(Computer c){
		
		return computerDAO.update(c);
	}
	
	@Override
	public boolean deleteComputer(int id){
		
		return computerDAO.delete(id);
	}
	
	@Override
	public boolean computerExists(int id){
		
		return computerDAO.exists(id);
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
