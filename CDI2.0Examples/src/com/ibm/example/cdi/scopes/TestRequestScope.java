package com.ibm.example.cdi.scopes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet needs to be called several times to check that it is working or not
 */
@WebServlet("/TestRequestScope")
public class TestRequestScope extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private ScopeBean1 bean;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		if (null == bean.getName()) {
			out.println("Name not set, setting");
			bean.setName("Brian");
		} 
		out.println(bean.getName());
	}
}
