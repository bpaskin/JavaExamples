package com.ibm.utility;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.utility.Status.STATUS;

@Path("/")
public class StatusService  {
	
	private Logger log = LoggerFactory.getLogger(StatusService.class);

	@Inject
	Status status;
	
	@Inject 
	RetrieveFile sftp;
	
	@Inject 
	PasswordHelper security;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{default: .*}")
	public String getStatus() {
		log.debug("Getting status");
		return status.getStatus().name();
	}
	
	@GET
	@Path("/mainton")
	public String maintOn() {
		log.info("Setting Maintenance Mode ON");
		status.setStatus(STATUS.MAINTENANCE);
		return status.getStatus().name();
	}
	
	@GET
	@Path("/maintoff")
	public String maintoff() {
		log.info("Setting Maintenance Mode OFF");
		
		if (sftp.retrieveFile()) {
			status.setStatus(STATUS.UP);
		} else {
			status.setStatus(STATUS.DOWN);
		}
		
		return status.getStatus().name();
	}
	
	@GET
	@Path("/encrypt/{password}") 
	public String encrypt(@PathParam("password") String password) throws Exception {
		return security.encrypt(password);
	}
}
