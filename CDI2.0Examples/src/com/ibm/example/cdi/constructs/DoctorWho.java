package com.ibm.example.cdi.constructs;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DoctorWho {
	 
	private String doctor;

	@PostConstruct
	private void setDoctor() {
		this.doctor = "Jodie Whittaker";
		System.out.println("The current Doctor is " + getDoctor());
	}
	
	@PreDestroy
	private void regenerate() {
		System.out.println("Time to regenerate");
		this.doctor = null;
	}
	
	public String getDoctor() {
		return this.doctor;
	}
}
