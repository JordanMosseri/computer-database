package com.excilys.computerdatabase.service.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;
import com.excilys.computerdatabase.service.IComputerService;


@Service
@Transactional(readOnly = true)
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
	public Paging<ComputerDTO> getComputers(int offset, int limit, String word){
		
		//Get part of computers
		List<Computer> partOfComputers = null;
		//TODO retourne 0, voir le &&, voir si cest pas mieux dutiliser Page fourni
		if(word != null && !word.isEmpty()) {
			partOfComputers = computerDAO.findByNameLike(word, new PageRequest(offset/limit, limit)).getContent();
		}
		else {
			partOfComputers = computerDAO.findAll(new PageRequest(offset/limit, limit)).getContent();
		}
		
		
		//Returns Paging object
		Paging<ComputerDTO> page = new Paging<ComputerDTO>(offset, DTOMapper.convert(partOfComputers), (offset+1)/limit, (int) computerDAO.count());
		
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
		
		//TODO
		//Company Name is provided
		/*else if( computer.getCompany().getName() != null && !computer.getCompany().getName().trim().isEmpty() ) {
			
			//Company Name exists in db
			computer.getCompany().setId(companyDAO.getIdIfNameExists(computer.getCompany().getName()));
			
			if(computer.getCompany().getId()<0){
				computer.getCompany().setId(companyDAO.save(computer.getCompany().getName()));
			}
		}*/
		
		//Else companyId < 0 and companyName not entered
		
		if (!companyDAO.exists(computer.getCompany().getId())) {
			computer.getCompany().setId(-1);
		}
		
		computerDAO.save(computer);
		
		return true;
	}
	
	@PersistenceContext private EntityManager em;
	
	@Override
	@Transactional
	public boolean updateComputer(Computer c){

		computerDAO.save(c);
		
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
