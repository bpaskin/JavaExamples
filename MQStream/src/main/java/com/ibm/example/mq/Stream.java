package com.ibm.example.mq;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.ibm.example.bo.Result;

@MessageDriven(name="ActSpecInQ")
public class Stream implements MessageListener {	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
	private Put mq;
	
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void onMessage(Message message) {
    	logger.entering(this.getClass().getCanonicalName(), "onMessage");

    	try {
    		logger.info("receiving messages");
    				
    		ReactiveStreams.of(message.getBody(Result.class))
    			.filter( (v) -> v.getUrl().contains("https"))
    			.to(new Subscriber<Result>() {    				
					@Override
					public void onSubscribe(Subscription s) {
				    	logger.entering(this.getClass().getCanonicalName(), "onSubscribe");
						s.request(1);
				    	logger.exiting(this.getClass().getCanonicalName(), "onSubscribe");
					}
	
					@Override
					public void onNext(Result t) {
				    	logger.entering(this.getClass().getCanonicalName(), "entering");
				    	mq.publishMessages(Put.Queue.OUTQ, t);
				    	logger.exiting(this.getClass().getCanonicalName(), "entering");
					}
	
					@Override
					public void onError(Throwable t) {
						logger.log(Level.SEVERE, t.getMessage(), t);
						t.printStackTrace(System.err);
					}
	
					@Override
					public void onComplete() {						
					}
	    		})
    			.run();
    		
    		logger.info("received messages");
    		
    	} catch (Exception e) {
    		logger.log(Level.SEVERE, e.getMessage(), e);
    		e.printStackTrace(System.err);
    	}
    	
    	logger.exiting(this.getClass().getCanonicalName(), "onMessage");
    }
}