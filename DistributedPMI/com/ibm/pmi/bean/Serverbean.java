package com.ibm.pmi.bean;

import java.io.Serializable;
import java.util.List;

public class Serverbean implements Serializable {
	private static final long serialVersionUID = 1544249587452735438L;
	protected String serverName;
	protected String nodeName;
	protected List<ResourceBean> resourceBean;
	
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<ResourceBean> getResourceBean() {
		return resourceBean;
	}
	public void setResourceBean(List<ResourceBean> resourceBean) {
		this.resourceBean = resourceBean;
	}
}
