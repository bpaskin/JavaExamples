package com.ibm.jee.rest.service;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.jee.enterprise.UserDAO;
import com.ibm.jee.jpa.model.User;

@Path("manage")
public class UserManager {
	
	@Inject
	private UserDAO dao;

	@GET
	@Path("user/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUser(@PathParam("name") String name) {
		return dao.getUser(name);
	}
	
	@GET
	@Path("allusers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @NotNull List<User> getAllUsers() {
		return dao.listAllUsers();
	}
	
	@POST
	@Path("adduser")	
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(@Valid User user) {
		dao.addUser(user.getName());
		return Response.ok().build();
	}
	
	@GET
	@Path("clear")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response clearUsers() {
		dao.clear();
		return Response.ok().build();
	}
}
