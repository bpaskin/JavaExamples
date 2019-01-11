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
 * This is an example of using CDI with a Service Provider pattern
 */
@WebServlet("/ServiceProvider")
public class ServiceProvider extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private CityProvider provider; 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		City city = (City) provider.getBean("Rome");
		out.println("The city is " + city.getLocalName() + 
				" or called " + city.getEnglishName() + " and has a population of " +  city.getPopulation());
	}
}
