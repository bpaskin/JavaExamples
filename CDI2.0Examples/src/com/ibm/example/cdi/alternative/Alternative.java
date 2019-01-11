package com.ibm.example.cdi.alternative;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Will inject the alternative if placed in beans.xml
 */
@WebServlet("/Alternative")
public class Alternative extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject private Doctor doctor;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("My favorite Doctor is : " + doctor.favoriteDoctor());
	}
}
