package com.ibm.swagger.jaxrs.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Random;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.swagger.jaxrs.model.UserName;

@Path("user")
@Api(value = "General Nonsense")
public class UserManager {
	
	private static String CLASSNAME = UserManager.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value="Add a user")
	@ApiResponses(value={
			@ApiResponse(code=200,message="OK"),
			@ApiResponse(code=500,message="Something bad happened")
	})
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
	@ApiOperation(value="Hello World")
	@ApiResponses(value={
			@ApiResponse(code=200,message="OK"),
			@ApiResponse(code=500,message="Error Occurred")
	})
	public String hello() {
		LOGGER.entering(CLASSNAME, "hello");
		LOGGER.exiting(CLASSNAME, "hello");
		return "Hello from the Application";
	}
}
