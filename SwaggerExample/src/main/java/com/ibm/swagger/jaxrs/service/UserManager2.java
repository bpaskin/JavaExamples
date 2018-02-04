package com.ibm.swagger.jaxrs.service;

import java.util.Random;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.swagger.jaxrs.model.UserName;

@Path("user2")
public class UserManager2 {
	
	private static String CLASSNAME = UserManager.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserName addUser(String name) {
		LOGGER.entering(CLASSNAME, "addUser", name);
		UserName user = new UserName();
		user.setName(name);
		user.setId(new Random().nextLong());
		LOGGER.exiting(CLASSNAME, "addUser");
		return user;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hello() {
		LOGGER.entering(CLASSNAME, "hello");
		LOGGER.exiting(CLASSNAME, "hello");
		return "Hello from the Application";
	}
}
