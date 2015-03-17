package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.service.Service;
import com.excilys.computerdatabase.util.Constantes;

/**
 * Servlet implementation class ServletEditComputer
 */
//@WebServlet("/editComputer")
@Controller
@RequestMapping("/editComputer")
public class EditComputerServletValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IService service;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	@RequestMapping(method = RequestMethod.POST)
	public String doPost(ModelMap model,
			@RequestParam(value="idHidden", defaultValue="", required=false) final String idString,
			@RequestParam(value="computerName", defaultValue="", required=false) final String computerName,
			@RequestParam(value="introduced", defaultValue="", required=false) final String introduced,
			@RequestParam(value="discontinued", defaultValue="", required=false) final String discontinued,
			@RequestParam(value="companyId", defaultValue="", required=false) final String companyId
			){
	//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//PrintWriter p = response.getWriter();
		
		
		/*p.println("computerId="+NumberUtils.toInt(idString));
		p.println("intCompanyId="+NumberUtils.toInt(companyId));
		p.println("introduced="+introduced);*/
		
		ComputerDTO cdto = new ComputerDTO(NumberUtils.toInt(idString), computerName, introduced, discontinued, new Company(NumberUtils.toInt(companyId)));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = service.updateComputer(c);
		
		//p.println(ok ? "Computer updated !" : "Error while updating the computer.");
		
		//getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
		
		
		
		return "forward:/Dashboard";
		
	}

}
