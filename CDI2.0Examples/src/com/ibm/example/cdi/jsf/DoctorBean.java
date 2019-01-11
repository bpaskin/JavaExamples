package com.ibm.example.cdi.jsf;

import javax.enterprise.inject.Model;

// @Model = @RequestScoped and defines an EL name

@Model
public class DoctorBean {
	
	private String name;

	public String getName() {
		System.out.println("Returning name " + this.name);
		return name;
	}

	public void setName(String name) {
		this.name = name;
		System.out.println("Setting name to " + this.name);
	}
}
