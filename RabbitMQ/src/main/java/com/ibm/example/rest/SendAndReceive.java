package com.ibm.example.rest;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/v1")
public class SendAndReceive {
	
	// The name in the JMSConnectionFactory must match the
	// connectionFactory jndiName in the Liberty server.xml
	@Inject
	@JMSConnectionFactory("jms/CF")
	private JMSContext context;
	
	
	// Send message to the Queue
	// curl -X POST http://host:port/RabbitMQ/v1/send/HelloPizza
	@POST
	@Path("/send/{message}")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendMessage(@PathParam("message") String message) {
		
		try {
			// Queue "test" must already exist on Rabbit MQ and not be durable
			Queue queue = context.createQueue("test");
			JMSProducer producer = context.createProducer();
			TextMessage textMessage = context.createTextMessage(message);
			producer.send(queue, textMessage);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "Error: " + e.getMessage();
		}
		return "Message sent successfully";
	}
	
	// Receive message from the Queue
	// curl -X GET http://host:port/RabbitMQ/v1/receive
	@GET
	@Path("/receive") 
	@Produces(MediaType.TEXT_PLAIN)
	public String receiveMessage() {
		
		try {
			// Queue "test" must already exist on Rabbit MQ and not be durable
			Queue queue = context.createQueue("test");
			JMSConsumer consumer = context.createConsumer(queue);
			Message message = consumer.receiveNoWait();
			
			if (message instanceof TextMessage) {
				return ((TextMessage) message).getText();
			} else {
				return "Message is not a TextMessage";
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return "Error: " + e.getMessage();
		}
	}
}
