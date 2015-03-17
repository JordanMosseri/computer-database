package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.util.Constantes;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//@WebServlet("/addComputer")
@Controller
@RequestMapping("/addComputer")
public class AddComputerServletValidation /*extends HttpServlet*/ {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IService service;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter p = response.getWriter();
		p.println("Methode GET. Ce servlet n'est accessible que via une requete POST.");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(ModelMap model,
			@RequestParam(value="computerName", defaultValue="", required=false) final String computerName,
			@RequestParam(value="introduced", defaultValue="", required=false) final String introduced,
			@RequestParam(value="discontinued", defaultValue="", required=false) final String discontinued,
			@RequestParam(value="companyId", defaultValue="", required=false) final String companyId
			){
	//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter p = response.getWriter();
		
		
		/*p.println(computerName);
		p.println(introduced);
		p.println(discontinued);
		p.println(companyId);*/
		
		int intCompanyId = NumberUtils.toInt(companyId);
		ComputerDTO cdto = new ComputerDTO(-1, computerName, introduced, discontinued, new Company(intCompanyId));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = service.addComputer(c);
		
		//p.println(ok ? "Computer added !" : "Error while adding the computer.");
		
		//getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
		
		//TODO forward:
		List<Company> companyList = service.getCompanies();
		model.addAttribute("companyList", companyList);
		model.addAttribute("formatString", Constantes.FORMAT_DATE);
		
		return "addComputer";
		
	}

}
