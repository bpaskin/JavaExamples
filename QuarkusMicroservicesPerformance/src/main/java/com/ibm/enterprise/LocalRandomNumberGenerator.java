package com.ibm.enterprise;

import java.security.SecureRandom;

import javax.inject.Singleton;

import com.ibm.rest.model.RandomResponse;

@Singleton
public class LocalRandomNumberGenerator {

	private SecureRandom random = new SecureRandom();
	
	public RandomResponse generateResponse(int min, int max) {
		RandomResponse response = new RandomResponse();
		response.setRandomNumber(random.nextInt(max - min) + min);
		response.setMoonFull(random.nextBoolean());
		return response;
	}
}
