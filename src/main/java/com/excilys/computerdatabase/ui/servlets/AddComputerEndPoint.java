package com.excilys.computerdatabase.ui.servlets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.util.Constantes;

@Controller
@RequestMapping("/Add")
public class AddComputerEndPoint {
	
	@Autowired
	IService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model){
		
		List<Company> companyList = service.getCompanies();
		model.addAttribute("companyList", companyList);
		
		model.addAttribute("formatString", Constantes.FORMAT_DATE);
		
		return "addComputer";
	}
}
