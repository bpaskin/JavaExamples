package com.ibm.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.sql.DataSource;

@ManagedBean
@SessionScoped
public class DSStatus implements Serializable {
	private static final long serialVersionUID = 6872513368605310969L;
	private static String CLASSNAME = DSStatus.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	private String jndiName;
	
	public String getJndiName() {
		LOGGER.entering(CLASSNAME, "getJndiName");
		LOGGER.exiting(CLASSNAME, "getJndiName");
		return jndiName;
	}
	public void setJndiName(String jndiName) {
		LOGGER.entering(CLASSNAME, "setJndiName", jndiName);
		this.jndiName = jndiName;
		LOGGER.exiting(CLASSNAME, "setJndiName");
	}
	
	public void testConnection() {
		LOGGER.entering(CLASSNAME, "testConnection");
		
		if (null == jndiName) {
			LOGGER.info("jndiName is null");
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("All fields must be completed"));
			LOGGER.exiting(CLASSNAME, "testConnection");
			return;
		} 
		
		try {
			InitialContext ic = new InitialContext();
			LOGGER.info("Looking up Datasource");
			DataSource ds = (DataSource) ic.lookup(getJndiName());
			LOGGER.info("Have Datasource");
			Connection conn = ds.getConnection();
			LOGGER.info("Have Conneciton");
			DatabaseMetaData md = conn.getMetaData();
			LOGGER.info("Have DB Metadata");
			
			// Get DB Info
			String productName = md.getDatabaseProductName();
			String productVersion = md.getDatabaseProductVersion();
			LOGGER.info("Have Product Info");

			// Read the tables to make sure that a connection is really present
			// can loop through ResultSet and get the tables, if needed
			LOGGER.info("Making call to DB for Tables");
			md.getTables(null, null, null, new String[]{"TABLE"});
			LOGGER.info("Received Tables");
			
			String outputMessage = "Connection Success! " + productName + " " + productVersion;

			LOGGER.info("About to close connection");
			conn.close();
			LOGGER.info("Closed connection");
			
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage(outputMessage));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Failed! " + e.getCause()));
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		LOGGER.exiting(CLASSNAME, "testConnection");
	}
}
