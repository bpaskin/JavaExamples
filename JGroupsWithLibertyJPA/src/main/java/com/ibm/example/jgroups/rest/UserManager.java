package com.ibm.example.jgroups.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.jgroups.enterprise.UserFacade;
import com.ibm.example.jgroups.model.Perfuser;

@Path("/v1/users")
public class UserManager {
	
	@Inject
	private UserFacade facade;

	@GET
	@Path("/getall")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Perfuser> getAllUsers() {
		return facade.findAllUsers();
	}
}
