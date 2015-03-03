package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;

import org.apache.commons.lang3.*;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/addComputer")
public class AddComputerServletValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServletValidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter p = response.getWriter();
		p.println("Methode GET. Ce servlet n'est accessible que via une requete POST.");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter p = response.getWriter();
		
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		p.println(computerName);
		p.println(introduced);
		p.println(discontinued);
		p.println(companyId);
		
		int intCompanyId = NumberUtils.toInt(companyId);
		ComputerDTO cdto = new ComputerDTO(-1, computerName, introduced, discontinued, new Company(intCompanyId));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = (new Service()).addComputer(c);
		
		p.println(ok ? "Computer added !" : "Error while adding the computer.");
		
		getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
	}

}