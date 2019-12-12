package com.ibm.enterprise;

import com.ibm.rest.model.RandomResponse;

import java.security.SecureRandom;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(RandomNumberGeneratorView.class)
public class RemoteRandomNumberGenerator implements RandomNumberGeneratorView {

	private static SecureRandom random = new SecureRandom();

	@Override
	public RandomResponse generateResponse(int min, int max) {
		RandomResponse response = new RandomResponse();
		response.setRandomNumber(random.nextInt(max - min) + min);
		response.setMoonFull(random.nextBoolean());
		return response;
	}
}
