package com.ibm.views;

import java.io.Serializable;
import java.security.Principal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.ibm.enterprise.beans.Randomize;

@ManagedBean
@RequestScoped
public class InfoBean implements Serializable {
	private static final long serialVersionUID = 1846424089640079900L;
	
	private String name;
	private String admin;
	
	@Inject 
	private Principal principal;
	
	@Inject 
	private HttpServletRequest request;
	
	@EJB
	private Randomize random;
	
	public String getName() {
		return principal.getName();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAdmin() {
		if (request.isUserInRole("Admins")) {
			return "User is an administrator";
		} 
		
		return "User is not an administrator";
	}
	
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	public int getRandom() {
		Randomize random = new Randomize();
		return random.getRandom();
	}
}
