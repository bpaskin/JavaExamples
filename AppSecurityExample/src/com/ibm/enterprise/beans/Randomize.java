package com.ibm.enterprise.beans;

import java.security.SecureRandom;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
public class Randomize {

	private static final SecureRandom random = new SecureRandom();
	
	@RolesAllowed("Admins")
	public int getRandom() {
		return random.nextInt(Integer.MAX_VALUE);
	}
	
	public int getRandomNoRoles() {
		return random.nextInt(Integer.MAX_VALUE);
	}
}
