package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.modele.Computer;

/**
 * Servlet implementation class ServletEditComputer
 */
@WebServlet("/editComputer")
public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditComputer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter p = response.getWriter();
		
		String idString = request.getParameter("idHidden");
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String companyId = request.getParameter("companyId");
		
		int computerId = Service.stringToInt(idString);
		Date dintroduced = Service.stringToDate(introduced);
		Date ddiscontinued = Service.stringToDate(discontinued);
		int intCompanyId = Service.stringToInt(companyId);
		
		p.println("computerId="+computerId);
		p.println("intCompanyId="+intCompanyId);
		
		Service s = new Service();
		boolean ok = s.updateComputer(new Computer(computerId, computerName, dintroduced, ddiscontinued, new Company(intCompanyId)));
		
		p.println(ok ? "Computer updated !" : "Error while updating the computer.");
		
		//getServletContext().getRequestDispatcher("/resultat.jsp").forward(request,response);
	}

}
