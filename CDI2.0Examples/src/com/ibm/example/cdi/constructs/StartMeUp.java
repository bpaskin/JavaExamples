package com.ibm.example.cdi.constructs;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class StartMeUp {

	@PostConstruct
	private void post() {
		System.out.println("StartMeUp Created!");
	}
	
	@PreDestroy
	private void destroy() {
		System.out.println("StartMeUp Destroy!");
	}
}
