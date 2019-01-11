package com.ibm.example.cdi.produces;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test @Produces.  Can be used on method or initial type.
 *
 */

@WebServlet("/TestProduces")
public class TestProduces extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject @Favorite String name;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("My favorite Doctor : " + name);
	}
}
