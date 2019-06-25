package com.ibm.tester.common;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class TestConnection {
	private static String CLASSNAME = TestConnection.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	public String test(String jndi) throws Exception {
		
		if (jndi == null) {
			throw new Exception("JNDI must not be null");
		}
		
		if (jndi.isEmpty()) {
			throw new Exception("JNDI must not be blank");
		}
		
		InitialContext ic = new InitialContext();
		LOGGER.info("Looking up Datasource");
		DataSource ds = (DataSource) ic.lookup(jndi);
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
		
		return outputMessage;
	}
}
