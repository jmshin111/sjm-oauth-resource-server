package com.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {
	
	@KafkaListener(topics = "${message.topic.name}", groupId = "{kafka.groupId}")
	public String Listen(String message) {
		String result = "Foo Received Message in group : " + message;
		return result;
	}
	
}
