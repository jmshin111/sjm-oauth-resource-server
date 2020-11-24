package com.sjm.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjm.kafka.producer.KafkaProducer;
import com.sjm.web.dto.Result;

@Controller
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(FooController.class);
	
	 @Autowired
	 private KafkaProducer messageProducer;
	
    @PreAuthorize("#oauth2.hasScope('write')")
    @RequestMapping(method = RequestMethod.POST, value = "/api/rpaResult/withphone")
    @ResponseBody
    
    public void RPAresult(@RequestBody Result result) {
    	
    	LOG.info(result.toString());
    	
    	String message = result.getphoneNumber();
    	
    	messageProducer.sendMessage(message);
    }
}
