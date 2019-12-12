package com.ibm.enterprise;

import java.security.SecureRandom;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ibm.rest.model.RandomResponse;

@Stateless
@LocalBean
public class LocalRandomNumberGenerator {

	private static SecureRandom random = new SecureRandom();
	
	public RandomResponse generateResponse(int min, int max) {
		RandomResponse response = new RandomResponse();
		response.setRandomNumber(random.nextInt(max - min) + min);
		response.setMoonFull(random.nextBoolean());
		return response;
	}
}
