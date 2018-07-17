package com.ibm.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/secure/logout")
public class SecurityLogout extends HttpServlet {
	private static final long serialVersionUID = -2477927972479662641L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.logout();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("../index.jsp");
	}

}
