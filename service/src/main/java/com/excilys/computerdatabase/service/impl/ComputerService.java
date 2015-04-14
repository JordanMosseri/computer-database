package com.excilys.computerdatabase.service.impl;
import java.util.List;

import javax.transaction.Transactional;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;
import com.excilys.computerdatabase.service.IComputerService;


@Service
public class ComputerService implements IComputerService {
	
	@Autowired
	ComputerPaginationRep computerDAO;
	
	@Autowired
	CompanyPaginationRep companyDAO;
	
	@Override
	public List<Computer> getComputers(){
		return computerDAO.findAll();
	}
	
	@Override
	public Paging<Computer> getComputers(int offset, int limit, String word){
		
		//Get part of computers
		List<Computer> partOfComputers = null;
		
		if(word != null) {
			if(!word.isEmpty()) {
				partOfComputers = computerDAO.findByNameLike(word, new PageRequest(offset/limit, limit)).getContent();
			}
		}
		else {
			partOfComputers = computerDAO.findAll(new PageRequest(offset/limit, limit)).getContent();
		}
		
		
		//Returns Paging object
		Paging<Computer> page = new Paging<Computer>(offset, partOfComputers, (offset+1)/limit, (int) computerDAO.count());
		
		return page;
	}
	
	@Override
	public Computer getComputer(int id){
		return computerDAO.findOne(id);
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param computer
	 * @return
	 */
	@Override
	@Transactional
	public boolean addComputer(Computer computer) {
		
		//If Company null
		if(computer.getCompany() != null) {
			
			//Company Id is provided
			if(computer.getCompany().getId() >= 0) {
				
				//Company Id doesn't exists in db
				if(!companyDAO.exists(computer.getCompany().getId())){
					throw new IllegalStateException("Inserting computer : companyId >= 0 but doesn't exists in database.");
				}
			}
			
			//Company Name is provided
			else if( computer.getCompany().getName() != null && !computer.getCompany().getName().trim().isEmpty() ) {
				
				//Try to get company with the name provided
				Company companyRetreived = companyDAO.findByName(computer.getCompany().getName());
				
				//Company exists in db with this name
				if(companyRetreived != null) {
					//Get the company'id into the computer object, to soon save it into the database
					computer.getCompany().setId(companyRetreived.getId());
				}
				
				//Company with this name doesn't exist, we create it
				else {
					Company companySaved = companyDAO.save(computer.getCompany());
					//computer.getCompany().setId(companySaved.getId());
					computer.setCompany(companySaved);
					
					computer.getCompany().getComputers().add(computer);
				}
			}
			
			//Finally, before adding the computer, we check the company id to avoid database constraints errors
			if (!companyDAO.exists(computer.getCompany().getId()) || computer.getCompany().getId() < 0) {
				computer.setCompany(null);
			}
		}
		
		computerDAO.saveAndFlush(computer);
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean updateComputer(Computer computer){

		addComputer(computer);
		
		return true;
	}
	
	@Override
	public boolean deleteComputer(int id){
		
		computerDAO.delete(id);
		
		return true;
	}
	
	@Override
	public boolean computerExists(int id){
		return computerDAO.exists(id);
	}
	
}
