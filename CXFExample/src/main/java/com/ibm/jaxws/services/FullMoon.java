package com.ibm.jaxws.services;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface FullMoon {
	
	@WebMethod(operationName="helpTheWitcher")
	public User tossACoinToYourWitcher();
}
