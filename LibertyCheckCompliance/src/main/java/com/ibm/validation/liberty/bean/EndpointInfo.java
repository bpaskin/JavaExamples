package com.ibm.validation.liberty.bean;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class EndpointInfo implements Serializable {
	private static final long serialVersionUID = 7397450437320958521L;

	private String host;
	private String name;
	
	@Min(-1)
	@Max(-1)
	private int httpPort;
	
	@Min(9443)
	private int httpsPort;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	public int getHttpsPort() {
		return httpsPort;
	}
	public void setHttpsPort(int httpsPort) {
		this.httpsPort = httpsPort;
	}
}
