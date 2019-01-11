package com.ibm.example.cdi.events;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Events")
public class Events extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject @Doctor private Event<Regenerate> doctor;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		doctor.fire(new Regenerate());
		doctor.fireAsync(new Regenerate());
		out.println("Events fired");
	}
}
