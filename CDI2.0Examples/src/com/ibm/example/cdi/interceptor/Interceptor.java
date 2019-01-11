package com.ibm.example.cdi.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Example of using an interceptor, requires entry in the beans.xml
 *
 */

@WebServlet("/Interceptor")
public class Interceptor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject InterceptorBean bean; 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		bean.doHello();
		out.println("There should be in an entry in the system log.");
	}
}
