package com.ibm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import com.ibm.jaxws.client.GetRandom;
import com.ibm.jaxws.client.GetRandomService;
import com.ibm.jaxws.client.ResponseMessage;

@WebServlet("/secure/wsclient")
public class WSClient extends HttpServlet {
	private static final long serialVersionUID = -3452629235205045223L;
	
	@WebServiceRef(wsdlLocation = "https://192.168.1.113:9443/AppSecurityExample/GetRandomService?WSDL")
	private static GetRandomService service; 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		GetRandom port = service.getGetRandomPort();
		
		BindingProvider bp = (BindingProvider)port;
		
		// Normally the userid/pw would be injected and be encoded
		bp.getRequestContext().put("ws-security.username", "user1");
		bp.getRequestContext().put("ws-security.password", "user1");
		
		ResponseMessage message = port.getRandom();
		out.println("client response:" + message.getNumber());
	}

}
