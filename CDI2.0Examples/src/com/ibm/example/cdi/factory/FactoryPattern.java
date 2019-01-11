package com.ibm.example.cdi.factory;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Example of a factory pattern.  This does not use CDI, but is an example of a use before
 * using CDI
 */

@WebServlet("/FactoryPattern")
public class FactoryPattern extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        City city = CityFactory.getCity("Rome");
        out.println("The city is " + city.getLocalName() +  " or called " + city.getEnglishName() +
        		" and has a population of " + city.getPopulation());	
	}
}
