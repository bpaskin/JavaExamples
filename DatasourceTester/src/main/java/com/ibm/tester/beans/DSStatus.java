package com.ibm.tester.beans;

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

import com.ibm.tester.common.TestConnection;

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
			TestConnection testConnection = new TestConnection();
			String outputMessage = testConnection.test(getJndiName());
			FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage(outputMessage));
		} catch (Exception e) {
			if (e.getCause() == null) {
				FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Failed! " + e.getMessage() + " could not be found"));
			} else {
				FacesContext.getCurrentInstance().addMessage("errorMsg", new FacesMessage("Failed! " + e.getMessage()));
			}
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		LOGGER.exiting(CLASSNAME, "testConnection");
	}
}