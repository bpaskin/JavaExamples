package com.ibm.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/")
public class ConnectionCheck extends HttpServlet {
	private static final long serialVersionUID = -8533907564643571014L;

	@Resource(lookup = "jdbc/users")
	private DataSource ds;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		try {
			Connection conn = ds.getConnection();
			DatabaseMetaData md = conn.getMetaData();
			String productName = md.getDatabaseProductName();
			String productVersion = md.getDatabaseProductVersion();
			
			out.println("Connection to DB was successful!");
			out.println(productName + " : " + productVersion);
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace(System.err);
			e.printStackTrace(out);
		}
		
		out.println("Served at: " + request.getContextPath());
	}
}
