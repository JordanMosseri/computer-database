package com.excilys.computerdatabase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.persistence.CompanyPaginationRep;
import com.excilys.computerdatabase.persistence.ComputerPaginationRep;
import com.excilys.computerdatabase.service.ICompanyService;

@Service
public class CompanyService implements ICompanyService {
	
	@Autowired
	ComputerPaginationRep computerDAO;
	
	@Autowired
	CompanyPaginationRep companyDAO;
	
	@Override
	public List<Company> getCompanies(){
		return companyDAO.findAll();
	}
	
	@Override
	@Transactional
	public boolean deleteCompany(int id){
		
		//Delete computers linked to the company first (avoid exception due to constraint key)
		List<Computer> computers = computerDAO.findAll();
		for (Computer computer : computers) {
			if(computer.getCompany() != null) {
				if(computer.getCompany().getId() == id) {
					computerDAO.delete(computer);
				}
			}
		}
		
		//Delete company
		if(companyDAO.exists(id)) {
			companyDAO.delete(id);
			return true;
		}
		else {
			return false;
		}
	}
	
}
