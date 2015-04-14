package com.excilys.computerdatabase.webservice;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.mappers.DTOMapper;
import com.excilys.computerdatabase.modele.ComputerDTO;
import com.excilys.computerdatabase.modele.Paging;
import com.excilys.computerdatabase.service.IComputerService;

/**
 * Functions of this webservice class simply call functions from the service module/layer.
 * @author Jordan Mosseri
 *
 */
@Component
@Path("/computerWebService")
public class ComputerWebService {
	
	@Autowired
	IComputerService service;
 
	//Functions to send Objects

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComputerDTO> getComputers() {
		return DTOMapper.convert(service.getComputers());
	}

	@GET
	@Path("/getPart/{offset}/{limit}/{word}")
	@Produces(MediaType.APPLICATION_JSON)
	public Paging<ComputerDTO> getComputers(@PathParam("offset") int offset, @PathParam("limit") int limit, @PathParam("word") String word) {
		return DTOMapper.convert(service.getComputers(offset, limit, word));
	}

	@GET
	@Path("/get/{param}")
	@Produces(MediaType.APPLICATION_JSON)
	public ComputerDTO getComputer(@PathParam("param") int id) {
		return DTOMapper.convert(service.getComputer(id));
	}

	//Functions to receive Objects
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComputer(ComputerDTO computerTDO) {
		return Response.status(200).entity(service.addComputer(DTOMapper.convert(computerTDO))).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateComputer(ComputerDTO computerTDO) {
		return Response.status(200).entity(service.updateComputer(DTOMapper.convert(computerTDO))).build();
	}

	//Functions that do not deal with Objects
	
	@DELETE
	@Path("/delete/{param}")
	public Response deleteComputer(@PathParam("param") int id) {
		return Response.status(200).entity(service.deleteComputer(id)).build();
	}

	@GET
	@Path("/exists/{param}")
	public Response computerExists(@PathParam("param") int id) {
		return Response.status(200).entity(service.computerExists(id)).build();
	}
 
}
