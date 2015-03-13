package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.service.Service;

//@WebServlet("/EditComputer")
@Controller
@RequestMapping("/EditComputer")
public class EditComputerServlet /*extends HttpServlet*/ {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String doGet(ModelMap model,
			@RequestParam(value="id", defaultValue="", required=false) final String idParam
			){
	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int intId;
		ComputerDTO computer;
		
	    if (idParam == null) {
	    	intId=-1;
	    } else {
	    	intId = NumberUtils.toInt( idParam );
	    }
	    
	    computer = DTOMapper.convert(service.getComputer(intId));
		
	    model.addAttribute("intId", intId);
	    model.addAttribute("computer", computer);
	    
		List<Company> companyList = service.getCompanies();
		
		model.addAttribute("companyList", companyList);
	    
	    //getServletContext().getRequestDispatcher("/static/views"+"/editComputer.jsp").forward(request,response);
		
		return "editComputer";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
