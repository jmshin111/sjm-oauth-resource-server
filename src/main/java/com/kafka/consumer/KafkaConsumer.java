package com.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;

@Controller
public class KafkaConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "test")
	public void listener(String message) {
		String result = "Foo Received Message in group : " + message;
	
		LOG.info(message);
	}
	
}
