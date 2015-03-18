package com.excilys.computerdatabase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.persistance.ICompanyDAO;
import com.excilys.computerdatabase.persistance.IComputerDAO;

@Service
public class CompanyService implements ICompanyService {
	
	@Autowired
	IComputerDAO computerDAO;
	
	@Autowired
	ICompanyDAO companyDAO;
	
	@Override
	public List<Company> getCompanies(){
		
		return companyDAO.getAll();
	}
	
	@Override
	@Transactional
	public boolean deleteCompany(int id){
		
		//Delete computers linked to the company first (avoid exception due to constraint key)
		boolean ok1 = computerDAO.deleteThoseFromCompany(id);
		
		//Delete company
		boolean ok2 = companyDAO.delete(id);
		
		return ok1 && ok2;
	}
	
}
