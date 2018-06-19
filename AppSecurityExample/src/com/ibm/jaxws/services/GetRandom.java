package com.ibm.jaxws.services;

import javax.ejb.EJB;
import javax.jws.WebService;

import com.ibm.enterprise.beans.Randomize;
import com.ibm.rest.services.ResponseMessage;

@WebService()
public class GetRandom {

	@EJB
	private Randomize random;
	
	public ResponseMessage getRandom() {
		ResponseMessage message = new ResponseMessage();
		message.setNumber(random.getRandomNoRoles());
		return message;
	}
}
