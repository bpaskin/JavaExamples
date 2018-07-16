package com.ibm.enterprise.session;

import java.util.logging.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@RunAs("LPAdmin")
@Singleton
public class TimerService {
	private static String CLASSNAME = TimerService.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	
	@EJB private ListenerPortService listenerPort;

	@Schedule(second="*/30", minute="*", hour="*", persistent=false) 
	public void checkStatus() {
		LOGGER.entering(CLASSNAME, "checkStatus()");
		listenerPort.getStatus();
		LOGGER.exiting(CLASSNAME, "checkStatus()");
	}
}
