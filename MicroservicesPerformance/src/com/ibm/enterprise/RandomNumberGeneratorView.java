package com.ibm.enterprise;

import com.ibm.rest.model.RandomResponse;

public interface RandomNumberGeneratorView {

	public RandomResponse generateResponse(int min, int max);
}
