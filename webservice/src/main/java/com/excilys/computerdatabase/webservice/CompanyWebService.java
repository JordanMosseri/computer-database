package com.excilys.computerdatabase.webservice;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.modele.Company;
import com.excilys.computerdatabase.service.ICompanyService;

/**
 * Functions of this webservice class simply call functions from the service module/layer.
 * @author Jordan Mosseri
 *
 */
@Component
@Path("/companyWebService")
public class CompanyWebService {
	
	@Autowired
	ICompanyService service;
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getCompanies() {
		return service.getCompanies();
	}
	
	@DELETE
	@Path("/delete/{param}")
	public Response deleteCompany(@PathParam("param") int id) {
		return Response.status(200).entity(service.deleteCompany(id)).build();
	}

}
