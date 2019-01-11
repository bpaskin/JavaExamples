package com.ibm.example.cdi.interceptor;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InterceptorBean {
	
	@Logging
	public String doHello() {
		return "Hello";
	}
}
