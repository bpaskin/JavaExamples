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

@WebServlet("/cdi")
public class cdi20 extends HttpServlet {
	private static final long serialVersionUID = -7772182151166754003L;
	
	@Inject
	@Information
	private Event<String> event;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		PrintWriter out = response.getWriter();
		
		if (null == event) {
			out.println("event not fired, event is null.");
		} else {
			event.fire("hello");
			out.println("event fired");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
