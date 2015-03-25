package com.excilys.computerdatabase.persistance;

import java.util.List;

import com.excilys.computerdatabase.modele.Company;

public interface ICompanyDAO {

	public abstract List<Company> findAll();

	/**
	 * Insert a company in bdd.
	 * @param nomFab
	 * @return Id de la company nouvellement cree
	 */
	public abstract int save(String nomFab);

	/**
	 * Check if a company name exists.
	 * @param nomFab 
	 * @return id of the existing company, or -1 if it doen't exists
	 */
	public abstract int getIdIfNameExists(String nomFab);

	public abstract boolean exists(int id);

	public abstract boolean delete(int id);

}