package com.ibm.liberty.mq.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

@ManagedBean
@SessionScoped
public class MQStatus {

	private String qcf;
	private String queue;
	private String action;
	private String message;
	
	public String getQcf() {
		return qcf;
	}
	public void setQcf(String qcf) {
		this.qcf = qcf;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAction() {
		if (null == action) {
			return "PUT";
		}
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public void checkMQ() {
		if (null == action || null == queue || null == qcf) {
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("All fields must be completed"));
			return;
		} 
		
		ConnectionFactory cf;
		Destination destination;
		InitialContext ctx;
		Connection conn;
		Session session;
		String corrId = null;
		
		//lookup elements
		try {
			ctx = new InitialContext();
			cf = (ConnectionFactory) ctx.lookup(qcf);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in QCF lookup : " + e.getMessage()));
			return;
		}
		
		try {
			ctx = new InitialContext();
			destination = (Destination) ctx.lookup(queue);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in Queue lookup : " + e.getMessage()));
			return;
		}
		
		try {
			conn = cf.createConnection();
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			conn.start();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in connection : " + e.getMessage()));
			return;
		}
		
		if (action.equalsIgnoreCase("PUT") || action.equalsIgnoreCase("PUTGET")) {
			try {
				MessageProducer msgProducer = session.createProducer(destination);
				TextMessage message = session.createTextMessage("Test message from MQ test program");
				corrId = message.getJMSCorrelationID();
				msgProducer.send(message);
			} catch (Exception e) {
				e.printStackTrace(System.err);
				FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in PUT message : " + e.getMessage()));
				return;
			}
		}
		
		if (action.equalsIgnoreCase("GET") || action.equalsIgnoreCase("PUTGET")) {
			try {
				if (null == corrId) {
					MessageConsumer msgConsumer = session.createConsumer(destination);
					msgConsumer.receive(2000);
				} else {
					MessageConsumer msgConsumer = session.createConsumer(destination, "JMSCorrelationID=" + corrId);
					msgConsumer.receive();
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
				FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in GET message : " + e.getMessage()));
				return;
			}
		}
		
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Error in closing connection : " + e.getMessage()));
		}
		
		FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Opertions completed"));
	}	
}
