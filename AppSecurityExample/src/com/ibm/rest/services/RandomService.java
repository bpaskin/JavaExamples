package com.ibm.rest.services;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ibm.enterprise.beans.Randomize;

@Path("/Random")
public class RandomService {
	
	@EJB
	private Randomize random;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseMessage getRandomNumber() {
		ResponseMessage message = new ResponseMessage();
		message.setNumber(random.getRandom());
		return message;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/AllRoles")
	@RolesAllowed({"Users", "Admin"})
	public ResponseMessage getRandomNumberNoRoles() {
		ResponseMessage message = new ResponseMessage();
		message.setNumber(random.getRandomNoRoles());
		return message;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/ajax")
	@RolesAllowed({"Users", "Admin"})
	public Response getRandomNumberAjax(@Context HttpHeaders headers) {
		String requestedHeader = headers.getHeaderString("X-Requested-With");
		
		if (null == requestedHeader) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (!requestedHeader.equalsIgnoreCase("XMLHttpRequest")) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		ResponseMessage message = new ResponseMessage();
		message.setNumber(random.getRandomNoRoles());
		return Response.status(Status.OK).entity(message).build();
	}
}
