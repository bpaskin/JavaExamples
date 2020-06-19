package com.ibm.example.jaxrs;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/v1")
public class TestJMS {
	
	@Resource(lookup = "jms/CF")
	private ConnectionFactory cf;
	
	@Resource(lookup = "jms/Q")
	private Queue q;
	
	// curl -d "My Test Message" -X POST http://host:port/ActiveMQ/v1/send -v
	@Path("/send") @POST @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendMessage(String message) {
		try { 
			Connection conn = cf.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			TextMessage msg = session.createTextMessage(message);
			session.createProducer(q).send(msg);
			conn.close();
		} catch (JMSException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} 
		return Response.status(Response.Status.CREATED).build();
	}
	
	// curl http://host:port/ActiveMQ/v1/get -v
	@Path("/get") @GET @Produces(MediaType.APPLICATION_JSON)
	public Response getMessage() {
		String message;
		try { 
			Connection conn = cf.createConnection();
			Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
			TextMessage msg = (TextMessage) session.createConsumer(q).receive(100);
			
			if (null == msg) {
				message = "No message found";
			} else {
				message = msg.getText();
			}
			conn.close();
		} catch (JMSException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} 
		return Response.status(Response.Status.OK).entity(message).build();
	}
}
