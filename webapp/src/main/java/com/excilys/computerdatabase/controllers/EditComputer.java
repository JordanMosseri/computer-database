package com.excilys.computerdatabase.controllers;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.ICompanyService;
import com.excilys.computerdatabase.service.IComputerService;

/**
 * Controller for the edit functionality. 
 * @author excilys
 *
 */
@Controller
public class EditComputer  {
	
	@Autowired
	IComputerService computerService;
	
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value = "/editComputer**", method = RequestMethod.GET)
	public String endPoint(ModelMap model,
			@RequestParam(value="id", defaultValue="", required=false) final String idParam
			){
		
		int intId;
		ComputerDTO computer;
		
	    if (idParam == null) {
	    	intId=-1;
	    } else {
	    	intId = NumberUtils.toInt( idParam );
	    }
	    
	    computer = DTOMapper.convert(computerService.getComputer(intId));
		
	    model.addAttribute("intId", intId);
	    model.addAttribute("computer", computer);
		model.addAttribute("companyList", companyService.getCompanies());
	    
		return "editComputer";
	}
	
	@RequestMapping(value = "/editComputer", method = RequestMethod.POST)
	public String action(ModelMap model,
			@RequestParam(value="idHidden", defaultValue="", required=false) final String idString,
			@RequestParam(value="computerName", defaultValue="", required=false) final String computerName,
			@RequestParam(value="introduced", defaultValue="", required=false) final String introduced,
			@RequestParam(value="discontinued", defaultValue="", required=false) final String discontinued,
			@RequestParam(value="companyId", defaultValue="", required=false) final String companyId
			){
		
		ComputerDTO cdto = new ComputerDTO(NumberUtils.toInt(idString), computerName, introduced, discontinued, new Company(NumberUtils.toInt(companyId)));
		
		boolean ok = computerService.updateComputer(DTOMapper.convert(cdto));
		
		return "forward:/Dashboard";
		
	}


}
