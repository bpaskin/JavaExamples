package com.ibm.example.spring.kafka;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class Publisher {
	private static final Logger log = LoggerFactory.getLogger(Publisher.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void publishMessage(String msg, String topic) {
		log.info("Publishing message");

		try {
			ListenableFuture<SendResult<String, String>> result = this.kafkaTemplate.send(topic, msg);

			SendResult<String, String> response = (SendResult) result.get();
			
			log.info("Message Publish : " + msg + " with offset " + response.getRecordMetadata().offset());
		} catch (Exception e) {
			throw new KafkaException(e);
		}		
	}
}
