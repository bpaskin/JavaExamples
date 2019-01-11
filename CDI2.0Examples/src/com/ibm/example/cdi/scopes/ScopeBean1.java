package com.ibm.example.cdi.scopes;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ScopeBean1 {
	
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
