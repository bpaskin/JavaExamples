package com.ibm.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/secure/logout")
@ServletSecurity(
		httpMethodConstraints = {
				@HttpMethodConstraint(value = "GET", rolesAllowed = {"Users", "Admin"}, 
						transportGuarantee = TransportGuarantee.CONFIDENTIAL),
				@HttpMethodConstraint(value = "POST", rolesAllowed = {})			
		}
)
public class Logout extends HttpServlet {
	private static final long serialVersionUID = -4633569976950614832L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.logout();
		response.sendRedirect(request.getContextPath());
	}
}
