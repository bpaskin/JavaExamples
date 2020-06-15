package com.ibm.example.services;

import javax.ejb.EJB;
import javax.jws.WebService;

import com.ibm.example.messaging.JMSDAO;

@WebService
public class SendReceive {
	
	@EJB
	private JMSDAO dao;
		
	public String sendMessage(String message) {
		var correlationId = dao.sendMessage(message);
		return dao.retrieveMessage(correlationId);
	}
	
	public String receiveMessage(String correlationId) {
		return dao.retrieveMessage(correlationId);
	}
}
