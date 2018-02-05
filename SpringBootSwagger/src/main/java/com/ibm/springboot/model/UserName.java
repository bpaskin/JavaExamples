package com.ibm.springboot.model;

import java.util.logging.Logger;

public class UserName {
	
	private static String CLASSNAME = UserName.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);

	protected long id;
	protected String name;
	
	public long getId() {
		LOGGER.entering(CLASSNAME, "getId");
		LOGGER.exiting(CLASSNAME, "getId");
		return id;
	}
	public void setId(long id) {
		LOGGER.entering(CLASSNAME, "setId", id);
		this.id = id;
		LOGGER.exiting(CLASSNAME, "setId");

	}
	public String getName() {
		LOGGER.entering(CLASSNAME, "getName");
		LOGGER.exiting(CLASSNAME, "getName");
		return name;
	}
	public void setName(String name) {
		LOGGER.entering(CLASSNAME, "setName", name);
		this.name = name;
		LOGGER.exiting(CLASSNAME, "setName", name);
	}	
}

