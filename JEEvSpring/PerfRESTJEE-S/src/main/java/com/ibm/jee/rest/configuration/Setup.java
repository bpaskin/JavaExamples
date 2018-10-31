package com.ibm.jee.rest.configuration;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.ibm.jee.rest.service.UserManager;

@ApplicationPath("rest")
public class Setup extends Application {
	
	@Override
	public Set<Object> getSingletons() {
		Set<Object> singletons = new HashSet<>();
		singletons.add(new UserManager());
		return singletons;
	}
}