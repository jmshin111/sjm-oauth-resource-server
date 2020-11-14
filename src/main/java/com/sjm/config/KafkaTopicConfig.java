package com.sjm.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

/**
 * A topic must exist to start sending messages to it.
 *
 * A KafkaAdmin bean is responsible for creating new topics in our broker.
 * With Spring Boot, a KafkaAdmin bean is automatically registered.
 */
public class KafkaTopicConfig {

	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name("reflectoring-1").build();
	}
	
	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name("reflectoring-2").build();
	}
	
}
