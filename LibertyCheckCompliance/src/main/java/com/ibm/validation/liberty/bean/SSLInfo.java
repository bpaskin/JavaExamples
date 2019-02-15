package com.ibm.validation.liberty.bean;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

public class SSLInfo implements Serializable {
	private static final long serialVersionUID = 3398615252471910825L;

	private String id;
	
	@Pattern(regexp="TLSv1.2")
	private String protocol;
	
	@Pattern(regexp="HIGH")
	private String securityLevel;
	
	@AssertTrue
	private boolean clientAuthSupported;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean isClientAuthSupported() {
		return clientAuthSupported;
	}
	public void setClientAuthSupported(boolean clientAuthSupported) {
		this.clientAuthSupported = clientAuthSupported;
	}
}
