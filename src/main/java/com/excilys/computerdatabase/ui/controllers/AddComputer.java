package com.excilys.computerdatabase.ui.controllers;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.ICompanyService;
import com.excilys.computerdatabase.service.IComputerService;
import com.excilys.computerdatabase.util.Constantes;

@Controller
//@RequestMapping("/Add")
public class AddComputer {
	
	@Autowired
	IComputerService computerService;
	
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value = "/Add", method = RequestMethod.GET)
	public String endPoint(ModelMap model){
		
		List<Company> companyList = companyService.getCompanies();
		model.addAttribute("companyList", companyList);
		
		model.addAttribute("formatString", Constantes.FORMAT_DATE);
		
		return "addComputer";
	}
	
	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public String action(ModelMap model,
			@RequestParam(value="computerName", defaultValue="", required=false) final String computerName,
			@RequestParam(value="introduced", defaultValue="", required=false) final String introduced,
			@RequestParam(value="discontinued", defaultValue="", required=false) final String discontinued,
			@RequestParam(value="companyId", defaultValue="", required=false) final String companyId
			){
		//PrintWriter p = response.getWriter();
		
		
		/*p.println(computerName);
		p.println(introduced);
		p.println(discontinued);
		p.println(companyId);*/
		
		int intCompanyId = NumberUtils.toInt(companyId);
		ComputerDTO cdto = new ComputerDTO(-1, computerName, introduced, discontinued, new Company(intCompanyId));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = computerService.addComputer(c);
		
		//p.println(ok ? "Computer added !" : "Error while adding the computer.");
		
		//getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
		
		//TODO forward:
		model.addAttribute("companyList", companyService.getCompanies());
		model.addAttribute("formatString", Constantes.FORMAT_DATE);
		
		return "addComputer";
		
	}
	
	
}
