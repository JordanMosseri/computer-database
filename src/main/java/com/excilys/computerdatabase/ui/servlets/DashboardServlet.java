package com.excilys.computerdatabase.ui.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.service.IService;

//@WebServlet(name="DashboardServlet", urlPatterns={"/Dashboard"})
@Controller
@RequestMapping("/Dashboard")
public class DashboardServlet /*extends HttpServlet*/ {
	//private static final long serialVersionUID = 1L;
	
	@Autowired
	IService service;
	
	
	public static final int DEFAULT_PAGE_SIZE = 15;
    

	//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView printHello(//ModelMap model,
			@RequestParam(value="offset", defaultValue="", required=false) final String offset,
			@RequestParam(value="pageSize", defaultValue="", required=false) final String pageSizeParam,
			@RequestParam(value="search", defaultValue="", required=false) final String searchParam,
			@RequestParam(value="lang", defaultValue="", required=false) final String lang
			) {
		
		ModelAndView model = new ModelAndView("dashboard");
		
		int intOffset = 0, pageSize = DEFAULT_PAGE_SIZE;
		Paging<ComputerDTO> paginationObject;
		String search = "";

	    if (offset != null) {
	    	intOffset = NumberUtils.toInt( offset );
	    }
	    
	    if (pageSizeParam != null && !pageSizeParam.isEmpty()) {
	    	pageSize = NumberUtils.toInt( pageSizeParam );
	    }
	    
	    if (searchParam != null) {
	    	search = searchParam;
	    }
	    
	    paginationObject = service.getComputers(intOffset, pageSize, search);
		
	    model.addObject("pageSize", pageSize);
	    model.addObject("paginationObject", paginationObject);
	    
	    
	    //getServletContext().getRequestDispatcher("/static/views"+"/dashboard.jsp").forward(request,response);
		
		//return "dashboard";
		return model;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
