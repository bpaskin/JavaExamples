package com.ibm.spring.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.spring.jpa.enterprise.UserDAO;
import com.ibm.spring.jpa.model.User;

@RestController
@RequestMapping("/rest/manage")
public class UserManager {
	
	@Autowired
	private UserDAO dao;

	@GetMapping(value="user/{name}")
	public List<User> getUser(@PathVariable String name) {
		return dao.findUser(name);
	}
	
	@GetMapping(value="allusers")
	public List<User> getAllUsers() {
		return dao.findAll();	
	}
	
	@PostMapping(value="adduser")
	@ResponseStatus(HttpStatus.OK)
	public void addUser(@RequestBody User userName) {
		User user = new User();
		user.setName(userName.getName());
		dao.save(user);
	}
	
	@GetMapping(value="clear")
	@ResponseStatus(HttpStatus.OK)
	public void clearUsers() {
		dao.deleteAll();
	}
}
