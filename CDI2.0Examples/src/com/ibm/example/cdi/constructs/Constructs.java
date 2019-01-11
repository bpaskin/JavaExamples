package com.ibm.example.cdi.constructs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test constructs of a bean
 */
@WebServlet("/Constructs")
public class Constructs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private DoctorWho doctor;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println(doctor.getDoctor());
	}
}
