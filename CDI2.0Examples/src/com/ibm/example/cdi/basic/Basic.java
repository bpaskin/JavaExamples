package com.ibm.example.cdi.basic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Example of Basic Injection
 */

@WebServlet("/Basic")
public class Basic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private City city;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("The city of " + city.getEnglishName() + " has a population of " + city.getPopulation());
	}
}
