package com.ibm.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;

public class PublisherStream implements Publisher<String> {
	
	 private final ExecutorService executor = ForkJoinPool.commonPool(); 
	 private SubmissionPublisher<String> publisher = new SubmissionPublisher<String>(executor, Flow.defaultBufferSize());
	
	// take the message and break it down into lines
	// using the Stream API search for the line with "User-Agent: "
	// and substring it after that value
	public void publishAgent(String messages) {
		
		var searchString = "User-Agent: ";
		
		var userAgent = messages
				.lines()
				.filter(line -> line.contains(searchString))
				.map(line -> line.substring(searchString.length()));
		
		publisher.submit(userAgent.findFirst().get());		
	}

	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		publisher.subscribe(subscriber);
	}
	

}
