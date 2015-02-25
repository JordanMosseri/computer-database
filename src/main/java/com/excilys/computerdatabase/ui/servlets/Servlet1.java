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
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/addComputer")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet1() {
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
		p.println("saluuuuuut !");
		
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		Date dintroduced = Service.stringToDate(introduced);
		Date ddiscontinued = Service.stringToDate(discontinued);
		int intCompanyId = Service.stringToInt(companyId);
		
		p.println(computerName);
		p.println(introduced);
		p.println(discontinued);
		p.println(companyId);
		
		Service s = new Service();
		boolean ok = s.addComputer(new Computer(-1, computerName, dintroduced, ddiscontinued, new Company(intCompanyId)));
		
		p.println(ok ? "Computer added !" : "Error while adding the computer.");
	}

}
