package com.ibm.utility;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.utility.Status.STATUS;

import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class Scheduler {
	
	private Logger log = LoggerFactory.getLogger(Scheduler.class);

	@Inject
	RetrieveFile sftp;
	
	@Inject
	Status status;
	
	@Scheduled(every = "{every.expr}")
	public void checkSFTP() {
		
		log.debug("Entering checkSFTP");
		
		if (STATUS.MAINTENANCE == status.getStatus()) {
			log.debug("Not checking ... in maintenance mode");
			return;
		} 
				
		if (sftp.retrieveFile()) {
			status.setStatus(STATUS.UP);
		} else {
			status.setStatus(STATUS.DOWN);
		}
		
		log.debug("Exiting checkSFTP");
	}
}
