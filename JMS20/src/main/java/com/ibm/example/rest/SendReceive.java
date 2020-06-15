package com.ibm.example.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.messaging.JMSDAO;

@Path("/v1")
public class SendReceive {
	
	@EJB
	private JMSDAO dao;
		
	
	@Path("/send")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String sendMessage(String message) {
		var correlationId = dao.sendMessage(message);
		return dao.retrieveMessage(correlationId);
	}
	
	@Path("/receive")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String receiveMessage(String correlationId) {
		return dao.retrieveMessage(correlationId);
	}
}
