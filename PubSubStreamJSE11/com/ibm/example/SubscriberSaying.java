package com.ibm.example;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SubscriberSaying implements Subscriber<String> {
	
	private Subscription subscription;
	private static final ArrayList<String> sinistar = new ArrayList<String>(List.of(
			"Beware, I live!", 
			"I hunger, coward!", 
			"I am Sinistar!", 
			"Run! Run! Run!", 
			"Beware, coward!", 
			"I hunger!", 
			"Run, coward!")
	);
	private static final SecureRandom random = new SecureRandom();
	
	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(String item) {
		
		// Randomly choose a saying
		System.out.println(sinistar.get(random.nextInt(sinistar.size())));
		subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace(System.err);
	}

	@Override
	public void onComplete() {}
}
