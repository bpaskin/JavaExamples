package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.example.cdi20.Information;

@WebServlet("/cdiasync")
public class cdi20async extends HttpServlet {
	private static final long serialVersionUID = -2785705225430460440L;

	@Inject
	@Information
	private Event<String> event;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		if (null == event) {
			out.println("event not fired, event is null.");
		} else {
			event.fireAsync("hello");
			
			// when complete check if there was an error and print out some info
			// cannot be printed to web, since the results return after the response
			// has been sent
			event.fireAsync("hello2").whenCompleteAsync((result, error) -> {
				if (null == error) {
					System.out.println("Event fired: " + result);
				} else {
                    System.out.println("Error in Async Event: " + error.getMessage());
				}
			});
			
			out.println("events fired");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
