package com.excilys.computerdatabase.mappers;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.modele.*;
//import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Utils;

public class DTOMapper {
	
	public static Computer convert(ComputerDTO c){
		
		return new Computer(c.getId(), c.getName(), Utils.convert(c.getDateAdded()), Utils.convert(c.getDateRemoved()), new Company(c.getCompanyName(), c.getCompanyId()));
	}
	
	public static ComputerDTO convert(Computer c){
		
		return new ComputerDTO(c.getId(), c.getName(), Utils.convert(c.getDateAdded()), Utils.convert(c.getDateRemoved()), c.getCompany());
	}
	
	public static List<ComputerDTO> convert(List<Computer> computers){
		ArrayList<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		
		for (Computer computer : computers) {
			computersDTO.add(DTOMapper.convert(computer));
		}
		
		return computersDTO;
	}
	
}
