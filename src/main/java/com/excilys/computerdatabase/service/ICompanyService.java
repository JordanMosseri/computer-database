package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.modele.Company;

public interface ICompanyService {
	
	public abstract List<Company> getCompanies();
	
	public abstract boolean deleteCompany(int id);

}
