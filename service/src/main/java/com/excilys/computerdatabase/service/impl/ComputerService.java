package com.excilys.computerdatabase.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistance.ICompanyDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;
import com.excilys.computerdatabase.service.IComputerService;


@Service
public class ComputerService implements IComputerService {
	
	@Autowired
	IComputerDAO computerDAO;
	
	@Autowired
	ICompanyDAO companyDAO;
	
	@Override
	public List<Computer> getComputers(){
		return computerDAO.findAll("");
	}
	
	@Override
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		
		//Get part of computers
		List<Computer> partOfComputers = computerDAO.getPart(offset, limit, word);
		//List<Computer> partOfComputers = computerDAO.myQueryGetPart(word, offset, limit);
		
		//Returns Paging object
		Paging<ComputerDTO> page = new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, (int) computerDAO.count());
		
		return page;
	}
	
	@Override
	public Computer getComputer(int id){
		
		return computerDAO.getOne(id);
	}
	
	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param computer
	 * @return
	 */
	@Override
	public boolean addComputer(Computer computer) {
		
		
		//Company Id is provided
		if(computer.getCompany().getId() >= 0) {
			
			//Company Id exists in db
			if(companyDAO.exists(computer.getCompany().getId())){
				//OK
			}
			
			//Wrong company id
			else{
				throw new IllegalStateException("Inserting computer : companyId >= 0 but doesn't exists in database.");
			}
		}
		
		//Company Name is provided
		else if( computer.getCompany().getName() != null && !computer.getCompany().getName().trim().isEmpty() ) {
			
			//Company Name exists in db
			computer.getCompany().setId(companyDAO.getIdIfNameExists(computer.getCompany().getName()));
			
			if(computer.getCompany().getId()<0){
				computer.getCompany().setId(companyDAO.save(computer.getCompany().getName()));
			}
		}
		
		//Else companyId < 0 and companyName not entered
		
		if (!companyDAO.exists(computer.getCompany().getId())) {
			computer.getCompany().setId(-1);
		}
		
		computerDAO.save(computer);
		
		return true;
	}
	
	
	@Override
	public boolean updateComputer(Computer c){
		
		computerDAO.update(c);
		//computerDAO.update(c.getName(), c.getDateAdded(), c.getDateRemoved(), c.getCompany().getId(), c.getId());
		
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
