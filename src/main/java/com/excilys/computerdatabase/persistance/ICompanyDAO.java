package com.excilys.computerdatabase.persistance;

import java.util.List;

import com.excilys.computerdatabase.modele.Company;

public interface ICompanyDAO {

	public abstract List<Company> findAll();

	/**
	 * Insere une company dans la bdd
	 * @param nomFab
	 * @return Id de la company nouvellement cree
	 */
	public abstract int save(String nomFab);

	/**
	 * Verifie si une company existe.
	 * @param nomFab nom de la company/fabriquant dont l'existance est a verifier
	 * @return Id de la company dans la bdd si celle-ci existe, -1 si celle-ci n'existe pas
	 */
	public abstract int getIdIfNameExists(String nomFab);

	public abstract boolean exists(int id);

	public abstract boolean delete(int id);

}