package com.ibm.example.mq;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.ibm.example.bo.Result;

@MessageDriven(name="ActSpecOutQ")
public class Consumer implements MessageListener {	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message message) {
    	logger.entering(this.getClass().getCanonicalName(), "onMessage");

    	try {
    		logger.info("receiving messages");
    		Result result = message.getBody(Result.class);
    		logger.info("received messages");
    		
    		logger.info(result.getUrl());  
    		System.out.println(result.getUrl());
    	} catch (Exception e) {
    		logger.log(Level.SEVERE, e.getMessage(), e);
    	}
    	
    	logger.exiting(this.getClass().getCanonicalName(), "onMessage");
    }

}
