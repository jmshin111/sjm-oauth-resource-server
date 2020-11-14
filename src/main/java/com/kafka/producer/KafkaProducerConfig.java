package com.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;

public class KafkaProducerConfig {

	@Value("${io.reflectoring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	/**
	 * BOOTSTRAP_SERVERS_CONFIG
	 * Host and port on which Kafka is running
	 * 
	 * KEY_SERIALIZER_CLASS_CONFIG
	 * Serializer class to be used for key.
	 * 
	 * VALUE_SERIALIZER_CLASS_CONFIG
	 * Serializer class to be used for value.
	 * 
	 * @return
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object>	props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, bootstrapServers);
		
		return props;
	}
	
	@Bean
	public ProducerFactory<String, String> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	/**
	 * KafkaTemplate
	 * helps us to send messages to their respective topic.
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}
