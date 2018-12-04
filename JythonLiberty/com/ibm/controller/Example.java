package com.ibm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;

@WebServlet("/example")
public class Example extends HttpServlet {
	private static final long serialVersionUID = -3919776538715554115L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		PythonInterpreter pi = new PythonInterpreter();
		pi.set("integer", new PyInteger(42));
		pi.exec("square = integer*integer");
		PyInteger square = (PyInteger)pi.get("square");
		
		out.println("Square of 42 is " + square);
		pi.close();
	}

}
