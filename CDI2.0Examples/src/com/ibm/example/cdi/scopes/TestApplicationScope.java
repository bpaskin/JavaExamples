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
 * Name should last throughout the application lifecycle
 */
@WebServlet("/TestApplicationScope")
public class TestApplicationScope extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Inject private ScopeBean2 bean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if (null == bean.getName()) {
			out.println("Name not set, setting");
			bean.setName("Brian");
		} 
		out.println(bean.getName());
	}
}
