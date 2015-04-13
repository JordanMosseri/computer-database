package com.excilys.computerdatabase.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.ICompanyService;
import com.excilys.computerdatabase.service.IComputerService;
import com.excilys.computerdatabase.util.LocaleUtils;

/**
 * Controller for the add functionality. 
 * @author Jordan Mosseri
 *
 */
@Controller
public class AddComputer {
	
	@Autowired
	IComputerService computerService;
	
	@Autowired
	ICompanyService companyService;
	
	/**
	 * Inject attributes into the ModelMap object.
	 * @param model
	 * @param atEndpoint
	 */
	private void prepareModel(ModelMap model, boolean atEndpoint){
		
		//TODO passer par les proprietes?
		model.addAttribute("formatString", LocaleUtils.getPatternOfCurrentLocale());
		
		if(atEndpoint) {
			//For the spring form validation/paths
			model.addAttribute("computerDTO", new ComputerDTO());
		}
		
		//For default select tag
		//model.addAttribute("companyList", companyService.getCompanies());
		
		//Converts to match spring form tag
		Map<Integer,String> country = new HashMap<Integer,String>();
		for (Company company : companyService.getCompanies()) {
			country.put(company.getId(), company.getName());
		}
		
		//For spring select tag
		model.addAttribute("companyList", country);
	}
	
	@RequestMapping(value = "/add**")
	public String endPoint(ModelMap model){
		
		prepareModel(model, true);
		
		return "addComputer";
	}
	
	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public String action(ModelMap model, @Valid ComputerDTO computerDTO, BindingResult result){
		
		if (result.hasErrors()) {
			
			prepareModel(model, false);
			
			return "addComputer";
		}
		else {
			boolean ok = computerService.addComputer(DTOMapper.convert(computerDTO));
			
			return "forward:/dashboard";
		}
	}
	
}
