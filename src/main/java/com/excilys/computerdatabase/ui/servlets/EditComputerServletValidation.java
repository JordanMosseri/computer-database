package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

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

/**
 * Servlet implementation class ServletEditComputer
 */
@WebServlet("/editComputer")
public class EditComputerServletValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServletValidation() {
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
		int intCompanyId = Service.stringToInt(companyId);
		
		p.println("computerId="+computerId);
		p.println("intCompanyId="+intCompanyId);
		p.println("introduced="+introduced);
		
		ComputerDTO cdto = new ComputerDTO(computerId, computerName, introduced, discontinued, new Company(intCompanyId));
		Computer c = DTOMapper.convert(cdto);
		
		boolean ok = (new Service()).updateComputer(c);
		
		p.println(ok ? "Computer updated !" : "Error while updating the computer.");
		
	}

}
