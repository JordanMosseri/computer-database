package com.excilys.computerdatabase.mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.*;
import com.excilys.computerdatabase.util.Constantes;

public class DTOMapper {
	
	public static Computer convert(ComputerDTO c){
		LocalDateTime dintroduced = Service.parse(c.dateAdded);
		LocalDateTime ddiscontinued = Service.parse(c.dateRemoved);
		
		return new Computer(c.id, c.name, dintroduced, ddiscontinued, c.company);
	}
	
	public static ComputerDTO convert(Computer c){
		String stringDateAdded = "";
		String stringDateRemoved = "";
		
		if (c.dateAdded != null) {
			//stringDateAdded = Constantes.dateFormat.format(c.dateAdded);
			stringDateAdded = c.dateAdded.format(DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE));
		}
		if (c.dateRemoved != null) {
			//stringDateRemoved = Constantes.dateFormat.format(c.dateRemoved);
			stringDateRemoved = c.dateRemoved.format(DateTimeFormatter.ofPattern(Constantes.FORMAT_DATE));
		}
		
		return new ComputerDTO(c.id, c.name, stringDateAdded, stringDateRemoved, c.company);
	}
	
	public static List<ComputerDTO> convert(List<Computer> computers){
		ArrayList<ComputerDTO> computersDTO = new ArrayList<ComputerDTO>();
		
		for (Computer computer : computers) {
			computersDTO.add(DTOMapper.convert(computer));
		}
		
		return computersDTO;
	}
	
}
