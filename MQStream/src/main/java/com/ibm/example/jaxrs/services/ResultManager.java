package com.ibm.example.jaxrs.services;

import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.bo.Result;
import com.ibm.example.bo.Search;
import com.ibm.example.mq.Put;

@RequestScoped
@Path("/search")
public class ResultManager {
	
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
	private Search search;
	
	@Inject
	private Put mq;
	
	@POST
	@Path("{term}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Result> search(@PathParam("term") String term) {
    	logger.entering(this.getClass().getCanonicalName(), "search");
    	
    	if (search == null) {
    		System.out.print("search is null");
    	}
    	
    	List<Result> results = search.findResults(term);
    	logger.fine("After finding results and about to publish " + results.size() + " results");
    	mq.publishMessages(Put.Queue.INQ, results.toArray(new Result[results.size()]));
    	
    	logger.exiting(this.getClass().getCanonicalName(), "search");
    	return results;
	}
}
