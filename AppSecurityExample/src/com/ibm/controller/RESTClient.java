package com.ibm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.rest.client.GetRandom;

@WebServlet("/restclient")
public class RESTClient extends HttpServlet {
	private static final long serialVersionUID = 5551673060226651838L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		GetRandom client = new GetRandom();
		out.println("client response: " + client.callRESTClient());
	}

}
