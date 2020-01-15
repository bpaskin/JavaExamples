package com.ibm.jaxws.services;

import javax.jws.WebService;

@WebService(portName = "WitcherPort", serviceName = "WitcherService", endpointInterface = "com.ibm.jaxws.services.FullMoon")
public class FullMoonInfo implements FullMoon {

	public User tossACoinToYourWitcher() {
		User u = new User();
		return u;
	}
}
