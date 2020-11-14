package com.sjm.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sjm.web.dto.Foo;

@Controller
public class FooController {

	private static final Logger LOG = LoggerFactory.getLogger(FooController.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
    public FooController() {
        super();
    }

    // API - read
    //@PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('read')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
    	
    	String message = "TEST Send Messages";
    	
    	ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("test", message);
    	
    	future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
    	      @Override
    	      public void onSuccess(SendResult<String, String> result) {
    	        LOG.info("Message [{}] delivered with offset {}",
    	          message,
    	          result.getRecordMetadata().offset());
    	      }
    	  
    	      @Override
    	      public void onFailure(Throwable ex) {
    	        LOG.warn("Unable to deliver message [{}]. {}", 
    	          message,
    	          ex.getMessage());
    	      }
    	    });
    	
        return new Foo(Long.parseLong(randomNumeric(2)), randomAlphabetic(4));
    }

    // API - write
    @PreAuthorize("#oauth2.hasScope('foo') and #oauth2.hasScope('write')")
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody final Foo foo) {
        foo.setId(Long.parseLong(randomNumeric(2)));
        
        return foo;
    }

}
