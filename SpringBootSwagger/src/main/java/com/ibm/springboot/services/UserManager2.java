package com.ibm.springboot.services;

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
public class UserManager2 {
	
	private static String CLASSNAME = UserManager2.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@POST
	@RequestMapping(path="/user2", method=RequestMethod.POST, 
		consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON)
	public UserName addUser(@RequestBody String name) {
		LOGGER.entering(CLASSNAME, "addUser", name);
		UserName user = new UserName();
		user.setName(name);
		user.setId(new Random().nextLong());
		LOGGER.exiting(CLASSNAME, "addUser");
		return user;
	}
	
	@RequestMapping(path="/user2", method=RequestMethod.GET,
		produces=MediaType.APPLICATION_JSON)
	public String hello() {
		LOGGER.entering(CLASSNAME, "hello");
		LOGGER.exiting(CLASSNAME, "hello");
		return "Hello from the Application";
	}
}
