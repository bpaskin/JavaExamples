package com.ibm.example.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/addusers")
public class SecurityAddUsers extends HttpServlet {
	private static final long serialVersionUID = 7298637620227171359L;
	
	@Resource(lookup="jdbc/users")
	private DataSource ds;
	
	@Inject
	private Pbkdf2PasswordHash passwordHash;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			Connection conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("INSERT INTO users VALUES('user1', '" + passwordHash.generate("user1".toCharArray()) + "')");
			stmt.execute("INSERT INTO groups VALUES('user1', 'user')");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace(out);
		}
	}
}
