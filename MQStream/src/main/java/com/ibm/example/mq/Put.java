package com.ibm.example.mq;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;

import com.ibm.example.bo.Result;

@Stateless
public class Put {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
    @Inject @JMSConnectionFactory("jms/QCF")
    private JMSContext ctx;
	
	@Resource(name="jms/InQ")
	private Destination inQ;
	
	@Resource(name="jms/OutQ")
	private Destination outQ;
	
	public enum Queue {
		INQ, OUTQ;
	}
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void publishMessages(Queue qName, Result... messages) {
    	logger.entering(this.getClass().getCanonicalName(), "publish");
    	
    	try {
    		for (final Result message : messages) {
        		logger.info("send messages");
        		if (qName == Queue.INQ) {
        			ctx.createProducer().send(inQ, message);
        		} else {
        			ctx.createProducer().send(outQ, message);
        		}
        		logger.info("sent messages");
    		}
    	} catch (Exception e) {
    		logger.log(Level.SEVERE, e.getMessage(), e);
    		e.printStackTrace(System.err);
    		ctx.rollback();
    	}
    	
    	logger.exiting(this.getClass().getCanonicalName(), "publish");
    }
}
