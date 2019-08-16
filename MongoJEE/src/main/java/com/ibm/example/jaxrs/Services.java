package com.ibm.example.jaxrs;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ibm.example.enterprise.MongoDAO;

@Path("/services")
public class Services {
	private Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());

	@EJB
	private MongoDAO dao;

	@Path("/listdbs")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getDBs() throws Exception {
		LOGGER.entering(this.getClass().getCanonicalName(), "getDBs");

		return dao.getDBNames();
	}
}
