package com.ibm.utility;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Status {
	
	enum STATUS { DOWN, UP, MAINTENANCE };
	
	private STATUS status = STATUS.DOWN;
	
	public STATUS getStatus() {
		return status;
	}
	
	public void setStatus(STATUS status) {
		this.status = status;
	}
}
