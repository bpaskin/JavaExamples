package com.ibm.tester.rest;

import javax.naming.NameNotFoundException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.tester.common.TestConnection;

@Path("/")
public class DSStatus {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("jndi")
	public String testDatasource (String jndi) {
		try {
			TestConnection testConnection = new TestConnection();
			return testConnection.test(jndi);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			
			if (e instanceof NameNotFoundException) {
				return "Failed! " + jndi + " could not be found.";
			}
			
			return "Failed! " + e.getMessage();
		} 
	}
}
