package com.ibm.example.messaging;

import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Stateless
public class JMSDAO {
	
	@Inject
	@JMSConnectionFactory("jms/QCF")
	JMSContext context;
	
	@Resource(name="jms/INQ")
	Queue inq;
	
	@Resource(name="jms/OUTQ")
	Queue outq;
	
	public String sendMessage(String message) {
		
		var text = context.createTextMessage(message);

		try {
			text.setJMSCorrelationID(UUID.randomUUID().toString());
			context.createProducer().send(inq, text); 
			return text.getJMSCorrelationID();
		} catch (JMSException e) {
			e.printStackTrace(System.err);
			return e.getMessage();
		}
	}
	
	public String retrieveMessage(String correctionId) {
		var selector = "JMSCorrelationID='" + correctionId +"'";
		System.out.println(selector);
		try {
			var message = context.createConsumer(outq, selector).receive(5000);
			
			return (null == message) ? "No message" : ((TextMessage) message).getText();
		} catch (JMSException e) {
			e.printStackTrace(System.err);
			return e.getMessage();
		}
	}
}
