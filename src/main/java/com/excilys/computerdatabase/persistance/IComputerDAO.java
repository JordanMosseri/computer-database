package com.excilys.computerdatabase.persistance;

import java.sql.Connection;
import java.util.List;

import com.excilys.computerdatabase.modele.Computer;

public interface IComputerDAO {

	public abstract Computer get(int idComputer, Connection cn);

	public abstract List<Computer> getAll(String endOfQuery, Connection cn);

	public abstract List<Computer> getPart(int offset, int limit, String word,
			Connection cn);

	/**
	 * Insert a computer, all checking is already done and comp.company.id is good, comp.company.name doesn't matter here
	 * @param comp
	 * @return
	 */
	public abstract boolean insert(Computer computer, Connection cn);

	public abstract boolean update(Computer computer, Connection cn);

	public abstract boolean delete(int id, Connection cn);

	public abstract boolean exists(int id, Connection cn);

	public abstract int getTotalCount(Connection cn);

	public abstract boolean deleteThoseFromCompany(int companyId, Connection cn);

	public abstract List<Computer> getThoseFromCompany(int companyId,
			Connection cn);

}