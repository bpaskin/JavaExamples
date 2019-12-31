package com.ibm.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.ibm.rest.model.RandomResponse;

@Path("/random")
public interface RandomizerClient {

	@GET
	@Path("inject/{min}/{max}")
	public RandomResponse getRandomInject(@PathParam ("min") int min,@PathParam ("max") int max);
	
	@GET
	@Path("local/{min}/{max}")
	public RandomResponse getRandomLocal(@PathParam ("min") int min,@PathParam ("max") int max);
	
	@GET
	@Path("rest/{min}/{max}")
	public RandomResponse getRandomREST(@PathParam ("min") int min,@PathParam ("max") int max);
}
