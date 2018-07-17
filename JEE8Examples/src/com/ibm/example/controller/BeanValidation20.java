package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.example.validation.BookInfo;

@WebServlet("/validation")
public class BeanValidation20 extends HttpServlet {
	private static final long serialVersionUID = -7670815063213913404L;
	
	private static Validator validator;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
				
		BookInfo book = new BookInfo();
		book.setTitle(" ");
		book.setEmail("pizza");
		LocalDate date = LocalDate.of(2019, Month.DECEMBER, 1);
		book.setReleaseDate(date);
		book.setCopiesSold(-1);

		Set<ConstraintViolation<BookInfo>> constraintViolations = validator.validate(book);
        Iterator<ConstraintViolation<BookInfo>> it = constraintViolations.iterator();
        while (it.hasNext()) {
        	out.println(it.next().getMessage());
        }
        
        String title = "{\"title\":\"\"}";
        
        Client client = ClientBuilder.newClient();
        Response clientResponse = client.target(request.getRequestURL() + "/jaxrs/book/get")
         	.request(MediaType.APPLICATION_JSON)
         	.post(Entity.entity(title, MediaType.APPLICATION_JSON));
        
        out.println("jax-rs calls:");
        out.println(clientResponse.getStatus());
        
        title = "{\"title\":\"If chins could talk\"}";
        clientResponse = client.target(request.getRequestURL() + "/jaxrs/book/get")
             	.request(MediaType.APPLICATION_JSON)
             	.post(Entity.entity(title, MediaType.APPLICATION_JSON));
        
        out.println(clientResponse.getStatus());
	}
}
