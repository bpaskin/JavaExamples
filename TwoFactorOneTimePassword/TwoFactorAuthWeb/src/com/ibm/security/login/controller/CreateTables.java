package com.ibm.security.login.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.security.login.AccessDAO;

@WebServlet("/CreateTables")
public class CreateTables extends HttpServlet {
	private static final long serialVersionUID = 8288765700616062448L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		try {
			new AccessDAO().createTables();
			out.println("Tables created");
		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}
}
