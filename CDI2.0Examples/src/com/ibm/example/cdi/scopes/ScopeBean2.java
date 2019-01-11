package com.ibm.example.cdi.scopes;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScopeBean2 {
	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
