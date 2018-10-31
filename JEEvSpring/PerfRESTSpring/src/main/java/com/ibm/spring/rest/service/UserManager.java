package com.ibm.spring.rest.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.spring.rest.model.User;

@RestController
@RequestMapping("/rest/manage")
public class UserManager {
	
	private final AtomicLong counter = new AtomicLong();
	private static final List<User> users = new CopyOnWriteArrayList<User>();

	@RequestMapping(value="user/{name}", method = RequestMethod.GET)
	public User getUser(@PathVariable String name) {
		return new User(counter.incrementAndGet(), name);
	}
	
	@RequestMapping(value="allusers", method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return users;
	}
	
	@RequestMapping(value="adduser", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addUser(@RequestBody User user) {
		users.add(user);
	}
	
	@RequestMapping(value="clear", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void clearUsers() {
		users.clear();
	}
}
