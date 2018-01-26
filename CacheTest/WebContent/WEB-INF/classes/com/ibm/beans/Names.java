package com.ibm.beans;

import java.security.SecureRandom;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.naming.InitialContext;

import com.ibm.websphere.cache.DistributedMap;

@RequestScoped
@ManagedBean
public class Names {
	
	private static String CLASSNAME = Names.class.getName();
	private static Logger LOGGER = Logger.getLogger(CLASSNAME);
	private static SecureRandom random = new SecureRandom();
	private String name;
	private long id;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		LOGGER.entering(CLASSNAME, "setName", name);
		
		this.name = name;
		
		try {
			DistributedMap cache = (DistributedMap) new InitialContext().lookup("cache/test");
			cache.put(random.nextLong(), name);
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "setName", e);
		}
		
		LOGGER.exiting(CLASSNAME, "setName");
	}
	
	public long getId() { 
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Object[] getCache() {
		LOGGER.entering(CLASSNAME, "listCache");
		DistributedMap cache = null;
		
		try {
			cache = (DistributedMap) new InitialContext().lookup("cache/test");
		} catch (Exception e) {
			LOGGER.throwing(CLASSNAME, "setName", e);
		}	
		
		LOGGER.exiting(CLASSNAME, "listCache");
		return cache.values().toArray();
	}
}
