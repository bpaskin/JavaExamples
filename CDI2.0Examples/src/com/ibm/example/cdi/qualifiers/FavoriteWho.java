package com.ibm.example.cdi.qualifiers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Example of using annotations to chose which Bean is selected, the default or annotated
 *
 */

@WebServlet("/FavoriteWho")
public class FavoriteWho extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Inject private FavoriteDoctor doctor1;
	@Inject @Favorite private FavoriteDoctor doctor2;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println(doctor1.getDoctor());
		out.println(doctor2.getDoctor());
	}
}
