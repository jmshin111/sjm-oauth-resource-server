package com.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {
	
	@KafkaListener(topics = "test")
	public String Listen(String message) {
		String result = "Foo Received Message in group : " + message;
		
		System.out.println("Received Message");
	
		return result;
	}
	
}
