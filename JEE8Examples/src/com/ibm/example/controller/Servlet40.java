package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

// affects the HttpServletMapping, change to see difference
@WebServlet({ "/Servlet40", "/servlet40" })
public class Servlet40 extends HttpServlet {
	private static final long serialVersionUID = 2492687006805954902L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		// push elements to client for future use.
		// use browser's F12 features to see what is returned
		// If image is not found a warning message will be shown in the messages.log
		PushBuilder pushBuilder = request.newPushBuilder();
		
		if (pushBuilder != null) {
			pushBuilder.path("images/Liberty.png").push();
			out.println("Pushed image");
		}
		
		// get the Servlet Mappings
		HttpServletMapping mapping = request.getHttpServletMapping();
        out.println("Mapping Match: " + mapping.getMappingMatch().name());
        out.println("Value Match: " +  mapping.getMatchValue());
        out.println("Pattern Match: " + mapping.getPattern());
        out.println("Servlet Name Match: " +  mapping.getServletName());
        
        // some new methods in ServletConext.
        out.println("Session timeout: " + request.getServletContext().getSessionTimeout());
		out.println("Character encoding: " + request.getServletContext().getRequestCharacterEncoding());
		out.println("Served at: " + request.getContextPath());
	}
}
