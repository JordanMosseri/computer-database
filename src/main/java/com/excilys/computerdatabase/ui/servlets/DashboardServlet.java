package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import com.excilys.computerdatabase.dao.ComputerDAO;
import com.excilys.computerdatabase.main.Service;
import com.excilys.computerdatabase.modele.Computer;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet(name="DashboardServlet", urlPatterns={"/Dashboard"})
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_PAGE_SIZE = 15;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int intOffset = 0, pageSize = DEFAULT_PAGE_SIZE;
		Paging<ComputerDTO> paginationObject;

	    if (request.getParameter("offset") == null) { }
	    else {
	    	intOffset = NumberUtils.toInt( request.getParameter("offset") );
	    }
	    
	    if (request.getParameter("pageSize") == null) { }
	    else {
	    	pageSize = NumberUtils.toInt( request.getParameter("pageSize") );
	    }
	    
	    paginationObject = (new Service()).getPartOfComputers(intOffset, pageSize); 
		
	    request.setAttribute("pageSize", pageSize);
	    request.setAttribute("paginationObject", paginationObject);
	    
	    getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
