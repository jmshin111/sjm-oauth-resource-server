package com.sjm.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.sjm.web.dto.Result;

@Controller
public class KafkaProducer {
	
    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Result> kafkaTemplate;
    
    public void sendMessage(Result message) {

        ListenableFuture<SendResult<String, Result>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Result>>() {

            @Override
            public void onSuccess(SendResult<String, Result> result) {
            	LOG.info("Sent message=[" + message.getphoneNumber() + " and " + message.getResult() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
            	LOG.info("Unable to send message=[" + message.getphoneNumber() + " and " + message.getResult() + "] due to : " + ex.getMessage());
            }
        });
    }
	
}
