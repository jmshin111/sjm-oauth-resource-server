package com.sjm.kafka.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjm.web.dto.Result;

@Configuration
public class KafkaProducerConfig {

	@Value("${kafka.bootstrapAddress}")
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
		
		ObjectMapper mapper = new ObjectMapper();
		Serde<Result> resultSerde = new JsonSerde<>(Result.class, mapper);

		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, resultSerde.serializer().getClass());

//		Map<String, Object>	props = new HashMap<>();
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return props;
	}
	
	@Bean
	public ProducerFactory<String, Result> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	/**
	 * KafkaTemplate
	 * helps us to send messages to their respective topic.
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, Result> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory(), true);
	}
}
