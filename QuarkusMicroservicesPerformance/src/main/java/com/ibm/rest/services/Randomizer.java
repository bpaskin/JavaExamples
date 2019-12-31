package com.ibm.rest.services;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.core.ExceptionAdapter;

import com.ibm.enterprise.LocalRandomNumberGenerator;
import com.ibm.rest.model.RandomResponse;

@Path("/random")
public class Randomizer {
	
	// needs to be package private instead of private
	@Inject
	LocalRandomNumberGenerator generator;
	
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
	// Not available in Quarkus
	
	// REST (Makes REST call to another server for data)
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("rest/{min}/{max}")
	public RandomResponse getRandomREST(@PathParam ("min") int min,@PathParam ("max") int max) {
		try {
			URI uri = new URI("http://localhost:9080/JAXRS1/services");
			RandomizerClient service = RestClientBuilder.newBuilder().baseUri(uri).build(RandomizerClient.class);
			RandomResponse response = service.getRandomLocal(-1000, 1000);		
			return response;
		} catch (URISyntaxException e) {
			e.printStackTrace(System.err);
			throw new ExceptionAdapter(e);
		}
	}
}
