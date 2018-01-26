package com.ibm.enterprise;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;

@Startup
@Singleton
public class Config {
	
	private static String CLASSNAME = Config.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	@PostConstruct
	public void initialize() {		
		try {
			LOGGER.entering(CLASSNAME, "initialize");
			new InitialContext().lookup("cache/test");
			LOGGER.exiting(CLASSNAME, "initialize");
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "initialize", e);
		}
	}
}
