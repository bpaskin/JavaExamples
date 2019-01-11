package com.ibm.example.cdi.decorator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test Decorators
 */

@WebServlet("/Decorator")
public class Decorator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject private LoggingEntry entry;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(entry.writeLogEntry("This is a message decorated"));
	}
}
