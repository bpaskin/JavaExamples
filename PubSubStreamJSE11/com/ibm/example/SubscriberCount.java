package com.ibm.example;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.atomic.AtomicInteger;

public class SubscriberCount implements Subscriber<String> {

	private Subscription subscription;
	private AtomicInteger count = new AtomicInteger(0);

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(String item) {
		System.out.println("recevied (" + count.incrementAndGet() + ") : " + item);
		subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace(System.err);
	}

	@Override
	public void onComplete() {}

}
