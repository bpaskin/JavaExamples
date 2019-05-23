package com.ibm.example.kafka.consumer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

public class StringConsumer implements Runnable {
	
	private KafkaConsumer<String, String> consumer;
	private AtomicBoolean isClosed = new AtomicBoolean();
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

	public StringConsumer (Properties properties, String... topics) {
    	logger.entering(this.getClass().getCanonicalName(), "constructor");
    	
		consumer = new KafkaConsumer<>(properties);
		consumer.subscribe(Arrays.asList(topics));
		
    	logger.exiting(this.getClass().getCanonicalName(), "constructor");
	}
	
	@Override
	public void run() {
    	logger.entering(this.getClass().getCanonicalName(), "run");
    	
		try {
			while (!isClosed.get()) {
				logger.fine("RUN: " + Thread.currentThread().getName());
				ConsumerRecords<String, String> records = consumer.poll(Duration.of(30000, ChronoUnit.MILLIS));
				for (ConsumerRecord<String, String> record : records) {
					logger.info("RUN: has record");
					System.out.println(record.value() + " : " + Thread.currentThread().getName());
				}
				logger.fine("RUN: commitSync");
				consumer.commitSync();
			}
	    	logger.exiting(this.getClass().getCanonicalName(), "run");
		} catch (WakeupException e) {
			// do nothing since it has awoken from the deep
		} finally {
			logger.fine("RUN: close " + Thread.currentThread().getName());
			consumer.close();
		}
	}
	
    public void stop() {
    	logger.entering(this.getClass().getCanonicalName(), "stop");
    	
        isClosed.set(true);
        consumer.wakeup();
        
    	logger.exiting(this.getClass().getCanonicalName(), "stop");
    }
}
