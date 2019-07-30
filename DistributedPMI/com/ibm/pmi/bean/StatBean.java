package com.ibm.pmi.bean;

import java.io.Serializable;

public class StatBean implements Serializable {
	private static final long serialVersionUID = -4730866008752189182L;
	protected String interval;
	protected String[] mbeans;
	protected String[] servers;

	public String getInterval() {
		return interval;
	}
	
	public void setInterval(String interval) {
		this.interval = interval;
	}
	
	public String[] getMbeans() {
		return mbeans;
	}

	public void setMbeans(String[] mbeans) {
		this.mbeans = mbeans;
	}
	public String[] getServers() {
		return servers;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}

}
