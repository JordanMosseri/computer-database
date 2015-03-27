package com.excilys.computerdatabase.persistance;

import java.util.List;

import com.excilys.computerdatabase.modele.Computer;

public interface IComputerDAO {

	public abstract Computer getOne(int idComputer);

	public abstract List<Computer> findAll(String endOfQuery);

	public abstract List<Computer> getPart(int offset, int limit, String word);

	/**
	 * Insert a computer, all checking is already done and comp.company.id is good, comp.company.name doesn't matter here
	 * @param comp
	 * @return
	 */
	public abstract boolean save(Computer computer);

	public abstract boolean update(Computer computer);

	public abstract boolean delete(int id);

	public abstract boolean exists(int id);

	public abstract int count();

	public abstract boolean deleteThoseFromCompany(int companyId);

	public abstract List<Computer> getThoseFromCompany(int companyId);

}