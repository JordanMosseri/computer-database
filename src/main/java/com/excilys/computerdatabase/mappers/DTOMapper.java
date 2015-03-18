package com.excilys.computerdatabase.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.modele.*;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.util.Constantes;

public class DTOMapper {
	
	public static Computer convert(ComputerDTO c){
		LocalDateTime dintroduced = ComputerService.parse(c.getDateAddedString());
		LocalDateTime ddiscontinued = ComputerService.parse(c.getDateRemovedString());
		
		return new Computer(c.getId(), c.getName(), dintroduced, ddiscontinued, new Company(c.getCompanyName(), c.getCompanyId()));
	}
	
	public static ComputerDTO convert(Computer c){
		String stringDateAdded = "";
		String stringDateRemoved = "";
		
		if (c.getDateAdded() != null) {
			//stringDateAdded = Constantes.dateFormat.format(c.dateAdded);
			stringDateAdded = c.getDateAdded().format(DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE));
		}
		if (c.getDateRemoved() != null) {
			//stringDateRemoved = Constantes.dateFormat.format(c.dateRemoved);
			stringDateRemoved = c.getDateRemoved().format(DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE));
		}
		
		return new ComputerDTO(c.getId(), c.getName(), stringDateAdded, stringDateRemoved, c.getCompany());
	}
	
	public static List<ComputerDTO> convert(List<Computer> computers){
		ArrayList<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		
		for (Computer computer : computers) {
			computersDTO.add(DTOMapper.convert(computer));
		}
		
		return computersDTO;
	}
	
}
