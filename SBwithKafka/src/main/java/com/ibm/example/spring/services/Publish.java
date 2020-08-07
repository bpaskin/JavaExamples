package com.ibm.example.spring.services;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.example.spring.kafka.Publisher;

@RestController
@RequestMapping("/rest")
public class Publish {
	private static final Logger log = LoggerFactory.getLogger(Publish.class);
	private static final AtomicLong counter = new AtomicLong();

	@Autowired
	Publisher publisher;

	@RequestMapping(value = "publish", method = RequestMethod.GET)
	public String addToCounter() {
		log.info("REST request received - sending message");
		publisher.publishMessage("Testing", "SpringTest");
		return "Messages published : " + counter.incrementAndGet();
	}

}
