package com.sjm.kafka.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.sjm.web.dto.Result;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.bootstrapAddress}")
    private String bootstrapServers;
	
    @Value(value = "${kafka.groupId}")
    private String groupId;
	
	/**
	 * BOOTSTRAP_SERVERS_CONFIG
	 * Host and port on which Kafka is running
	 * 
	 * @return
	 */
	@Bean
	public Map<String, Object> consumerConfigs() {
		
//		ObjectMapper mapper = new ObjectMapper();
		Serde<Result> resultSerde = new JsonSerde<>(Result.class);

		Map<String, Object>	props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, resultSerde.deserializer().getClass());
		
		return props;
	}
	
	@Bean
	public ConsumerFactory<String, Result> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}
	
	/**
	 * KafkaListenerContainer
	 * receive all message from all topics or patitions on a single thread
	 * 
	 * @return
	 */
	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Result>> kafkaListenerContainer(){
		ConcurrentKafkaListenerContainerFactory<String, Result> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		
		return factory;
	}
	
}
