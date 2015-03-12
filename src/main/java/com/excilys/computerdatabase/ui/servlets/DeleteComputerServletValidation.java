package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdatabase.service.IService;
import com.excilys.computerdatabase.service.Service;

/**
 * Servlet implementation class DeleteComputerServletValidation
 */
@WebServlet("/deleteComputer")
public class DeleteComputerServletValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IService service;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServletValidation() {
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
		
		String[] str = request.getParameter("selection").split(",");
		
		for (String string : str) {
			//p.print(string+"<br/>");
			
			boolean ok = service.deleteComputer(NumberUtils.toInt(string));
			
			p.println(ok ? "Computer #" + NumberUtils.toInt(string) + " deleted !" : "Error while deleting the computer.");
		}
		
		getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
	}

}
