package com.ibm.example.cdi.named;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * This is an example of using CDI with a Service Provider pattern that is the best way to get beans
 */

@WebServlet("/ServiceProvider3")
public class ServiceProvider3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private CityProvider2 provider;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		for (String city : provider.getAllCities()) {
			out.println("Found :" + city);
		}
			
		City city = provider.getCity("Rome");
		out.println("The city is " + city.getLocalName() + 
				" or called " + city.getEnglishName() + " and has a population of " +  city.getPopulation());
	}
}
