package com.ibm.example.cdi.named;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * This is an example of using CDI with a Service Provider pattern that is the best way to get beans
 */

@WebServlet("/ServiceProvider2")
public class ServiceProvider2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject @Named("Rome") private City city;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println(city.getEnglishName() + " has a population of " + city.getPopulation());
	}
}
