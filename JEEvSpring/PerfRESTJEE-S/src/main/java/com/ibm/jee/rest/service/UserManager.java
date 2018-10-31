package com.ibm.jee.rest.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.jee.rest.model.User;

@Path("manage")
public class UserManager {

	private final AtomicLong counter = new AtomicLong();
	private static final List<User> users = new CopyOnWriteArrayList<User>();

	@GET
	@Path("user/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("name") String name) {
		return new User(counter.incrementAndGet(), name);
	}
	
	@GET
	@Path("allusers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return users;
	}
	
	@POST
	@Path("adduser")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user) {
		users.add(user);
		return Response.ok().build();
	}
	
	@GET
	@Path("clear")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response clearUsers() {
		users.clear();
		return Response.ok().build();
	}
}
