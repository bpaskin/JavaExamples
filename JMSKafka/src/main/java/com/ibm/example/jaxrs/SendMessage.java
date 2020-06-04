package com.ibm.example.jaxrs;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.clients.producer.ProducerRecord;

import fish.payara.cloud.connectors.kafka.api.KafkaConnection;
import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory;

@Path("/v1")
@ApplicationScoped
public class SendMessage {
	
	private static SecureRandom random = new SecureRandom();
	private static final String[] pizzas = {"Margherita", "Napoli", "Capricciosa", "Rossa", "Diavola"};
	
	@Resource(lookup="jms/CF")
	KafkaConnectionFactory cf;
	
	@Path("send")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String send() {
		
		Date date = Calendar.getInstance().getTime();
		
		if (null == cf) {
			return "Connection Factory is null";
		} 

		try (KafkaConnection conn = cf.createConnection()) {
            conn.send(new ProducerRecord<String,String>("my-pizza",  pizzas[random.nextInt(pizzas.length)]));
            return "Message sent at " + date;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return  e.getMessage();
        }
	}
}
