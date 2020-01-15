package com.ibm.jaxws.services;

import java.security.SecureRandom;
public class User {

	private String name;
	private String phase;
	
	public String getName() {
		return "Geralt of Rivia";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhase() {	
		return new SecureRandom().nextBoolean() ? "Full" : "Not Full";
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	
}
