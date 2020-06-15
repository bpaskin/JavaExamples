package com.ibm.example.messaging;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

@MessageDriven
public class ProcessMessages implements MessageListener {
	
	@Inject
	@JMSConnectionFactory("jms/QCF")
	JMSContext context;
	
	@Resource(name="jms/OUTQ")
	Queue outq;
	
    public void onMessage(Message message) {
    	try {
    		System.out.println("Received : " + message.getJMSCorrelationID());
			context.createProducer().send(outq, message); 
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}    	
    }
}
