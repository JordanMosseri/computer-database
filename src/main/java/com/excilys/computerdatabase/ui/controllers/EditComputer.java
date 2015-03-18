package com.excilys.computerdatabase.ui.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.excilys.computerdatabase.service.ComputerService;

@Controller
//@RequestMapping("/EditComputer")
public class EditComputer  {
	
	@Autowired
	IComputerService computerService;
	
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value = "/EditComputer", method = RequestMethod.GET)
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
	    
	    //getServletContext().getRequestDispatcher("/static/views"+"/editComputer.jsp").forward(request,response);
		
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
		//PrintWriter p = response.getWriter();
		
		
		/*p.println("computerId="+NumberUtils.toInt(idString));
		p.println("intCompanyId="+NumberUtils.toInt(companyId));
		p.println("introduced="+introduced);*/
		
		ComputerDTO cdto = new ComputerDTO(NumberUtils.toInt(idString), computerName, introduced, discontinued, new Company(NumberUtils.toInt(companyId)));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = computerService.updateComputer(c);
		
		//p.println(ok ? "Computer updated !" : "Error while updating the computer.");
		
		//getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
		
		
		
		return "forward:/Dashboard";
		
	}


}
