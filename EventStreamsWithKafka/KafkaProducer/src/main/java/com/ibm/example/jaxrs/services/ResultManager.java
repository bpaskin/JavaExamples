package com.ibm.example.jaxrs.services;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.bo.Result;
import com.ibm.example.bo.Search;
import com.ibm.example.kafka.producer.ResultProducer;
import com.ibm.example.kafka.producer.StringProducer;

/**
 * Simple class that will accept a GET request on contextroot/jaxrs/search/hello and
 * send "hello" to be published.
 * 
 * The POST request listens on contextroot/jaxrs/search/{search term} and will
 * call google to get the URLs of the first 10 results returned and then
 * send those results to be published.  Requests are returned in JSON format.
 * 
 * @author: Brian S Paskin (IBM Italia)
 * @version: 1.0.0.0 (2019/05/15)
 * 
 **/
@RequestScoped
@Path("/search")
public class ResultManager {
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
	private StringProducer producer;
	
	@Inject
	private ResultProducer resultProducer;
	
	@Inject
	private Search search;

	@GET
	@Path("hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
    	logger.entering(this.getClass().getCanonicalName(), "hello");

    	producer.publish(null, "Hello");
        	
    	logger.exiting(this.getClass().getCanonicalName(), "hello");
		return "Hello World @ " + System.currentTimeMillis();
	}
	
	@POST
	@Path("{term}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Result> search(@PathParam("term") String term) {
    	logger.entering(this.getClass().getCanonicalName(), "search");
    	
    	List<Result> results = search.findResults(term);
    	logger.fine("After finding results and about to publish");
    	resultProducer.publish(null, results.toArray(new Result[results.size()]));
    	
    	logger.exiting(this.getClass().getCanonicalName(), "search");
    	return results;
	}
}
