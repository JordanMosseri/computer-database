package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;

public interface IComputerService {

	public abstract List<Computer> getComputers();

	public abstract Paging<ComputerDTO> getComputers(int offset, int limit,
			String word);
	
	public abstract Computer getComputer(int id);

	/**
	 * Check all cases, then modify comp.company.id value to be good
	 * @param comp
	 * @return
	 */
	public abstract boolean addComputer(Computer comp);

	public abstract boolean updateComputer(Computer c);

	public abstract boolean deleteComputer(int id);

	public abstract boolean computerExists(int id);

}