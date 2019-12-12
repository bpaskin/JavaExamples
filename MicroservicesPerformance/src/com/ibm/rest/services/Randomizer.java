package com.ibm.rest.services;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.ibm.enterprise.LocalRandomNumberGenerator;
import com.ibm.enterprise.RandomNumberGeneratorView;
import com.ibm.rest.model.RandomResponse;

@Path("/random")
public class Randomizer {
	
	@EJB
	private LocalRandomNumberGenerator generator;
	
	// Inject EJB
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("inject/{min}/{max}")
	public RandomResponse getRandomInject(@PathParam ("min") int min,@PathParam ("max") int max) {
		return generator.generateResponse(min, max);
	}
	
	
	// No Interface view
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("local/{min}/{max}")
	public RandomResponse getRandomLocal(@PathParam ("min") int min,@PathParam ("max") int max) {
		LocalRandomNumberGenerator random = new LocalRandomNumberGenerator();
		return random.generateResponse(min, max);
	}
	
	// Remote (Used normally when EJB is remote or in another App in the same App Server)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("remote/{min}/{max}")
	public RandomResponse getRandomRemote(@PathParam ("min") int min,@PathParam ("max") int max) {
		
		try { 
			InitialContext ic = new InitialContext();
			RandomNumberGeneratorView remote = (RandomNumberGeneratorView) ic.lookup("java:global/JAXRS1/RemoteRandomNumberGenerator!com.ibm.enterprise.RandomNumberGeneratorView");
			return (remote.generateResponse(min, max));
		} catch (Exception e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// REST (Makes REST call to another server for data)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("rest/{min}/{max}")
	public RandomResponse getRandomREST(@PathParam ("min") int min,@PathParam ("max") int max) {
		Client client = ClientBuilder.newClient();
		RandomResponse rr = client.target("http://localhost:9080/JAXRS1/services/random/local/" + min + "/" + max).request(MediaType.APPLICATION_JSON).get(RandomResponse.class);
		client.close();
		return rr;
	}
}
