package com.ibm.springboot.services;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Random;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.springboot.model.UserName;

@RestController
@RequestMapping("/services")
@Api(value = "General Nonsense")
public class UserManager {
	
	private static String CLASSNAME = UserManager.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@POST
	@RequestMapping(path="/user", method=RequestMethod.POST, 
		consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON)
	@ApiOperation(value="Add a user")
	@ApiResponses(value={
			@ApiResponse(code=200,message="OK"),
			@ApiResponse(code=500,message="Something bad happened")
	})
	public UserName addUser(@RequestBody String name) {
		LOGGER.entering(CLASSNAME, "addUser", name);
		UserName user = new UserName();
		user.setName(name);
		user.setId(new Random().nextLong());
		LOGGER.exiting(CLASSNAME, "addUser");
		return user;
	}
	
	@RequestMapping(path="/user", method=RequestMethod.GET,
		produces=MediaType.APPLICATION_JSON)
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
