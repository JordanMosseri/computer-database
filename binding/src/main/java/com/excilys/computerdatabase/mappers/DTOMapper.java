package com.excilys.computerdatabase.mappers;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.modele.*;

/**
 * To map DTO and non-DTO objects, and also List and Paging objects.
 * @author Jordan Mosseri
 *
 */
public class DTOMapper {
	
	/**
	 * Converts a ComputerDTO object to a Computer object
	 * @param c
	 * @return
	 */
	public static Computer convert(ComputerDTO c){
		
		return new Computer(c.getId(), c.getName(), DateMapper.convert(c.getDateAdded()), DateMapper.convert(c.getDateRemoved()), new Company(c.getCompanyName(), c.getCompanyId()));
	}
	
	/**
	 * Converts a Computer object to a ComputerDTO object
	 * @param c
	 * @return
	 */
	public static ComputerDTO convert(Computer c){
		
		return new ComputerDTO(c.getId(), c.getName(), DateMapper.convert(c.getDateAdded()), DateMapper.convert(c.getDateRemoved()), c.getCompany());
	}
	
	/**
	 * Converts a Computer list to a ComputerDTO list
	 * @param computers
	 * @return
	 */
	public static List<ComputerDTO> convert(List<Computer> computers) {
		ArrayList<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		
		for (Computer computer : computers) {
			computersDTO.add(DTOMapper.convert(computer));
		}
		
		return computersDTO;
	}
	
	/**
	 * Converts a Computer paging to a ComputerDTO paging
	 * @param computerPaging
	 * @return
	 */
	public static Paging<ComputerDTO> convert(Paging<Computer> computerPaging) {
		return new Paging<ComputerDTO>(computerPaging.getOffset(), DTOMapper.convert(computerPaging.getActualList()), computerPaging.getIndexPage(), computerPaging.getTotalSize());
	}
	
}
