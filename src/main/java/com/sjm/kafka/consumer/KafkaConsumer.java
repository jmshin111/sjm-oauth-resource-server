package com.sjm.kafka.consumer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class KafkaConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "${message.topic.name}", groupId = "${kafka.groupId}") 
	public void listener(String message) {

		String result = "Foo Received Message in group : " + message;
	
		LOG.info(result);
		
		RestTemplate restClient = new RestTemplate();
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
//		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
//		httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		httpHeaders.set("PhoneNumber", "+821068107782");
		
		HttpEntity reqEntity = null;

		reqEntity = new HttpEntity<String>(httpHeaders);
		
		String url = "http://ec2-52-198-19-6.ap-northeast-1.compute.amazonaws.com:3099";
		String apiPath = "/";
		HttpMethod method = HttpMethod.GET;
		
		// - API URL을 빌드해서 생성한다.
		String uri = UriComponentsBuilder
				.fromHttpUrl(url)
				.path(apiPath)
				.build()
				.toString();
		
		// 2. RestTemplate를 이용해서 API를 요청한다.
		HttpStatus respStatus = null;
		String respBody = null;
		
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		try {
			
			// Call Rest API
			/******************************************************************************************************/
//			requestLog(request.getRequestURI(), uri, method, reqEntity.getHeaders(), reqEntity.getBody());
//			ResponseEntity<String> respEntity = restClient.exchange(uri, method, reqEntity, String.class, uriVars);

			ResponseEntity<String> respEntity = restClient.exchange(uri, method, reqEntity, String.class);
			/******************************************************************************************************/
			
			// 예외 없이 정상적으로 처리 된 경우
			/******************************************************************************************************/
//			respStatus = respEntity.getStatusCode();
//			responseLog(request.getRequestURI(), respStatus, respEntity.getBody());
			/******************************************************************************************************/
			
//			if (respEntity.hasBody()) {	// body 데이터가 존재하는 경우
//				respBody = respEntity.getBody();
//			}
			
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			respStatus = e.getStatusCode();
//			errorLog(request.getRequestURI(), respStatus, e);
		} catch (UnknownHttpStatusCodeException e){
			respStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			errorLog(request.getRequestURI(), respStatus, e);
		} catch (Exception e) {
			respStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//			errorLog(request.getRequestURI(), respStatus, e);
		}		
		
		
	}
	
//	private void requestLog(String uri, String liscoreUri, HttpMethod method, HttpHeaders headers, Object requestBody) {
//		LOG.info("[ LIS-CORE REST Request ]");
//		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
//		LOG.info("request uri: {}", uri);
//		LOG.info("lis-core uri: {}", liscoreUri);
//		LOG.info("method: {}", method);
//		LOG.info("requestHeaders: {}", headers);
//		LOG.info("requestBody: {}", requestBody);
//		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
//	}
//	
//	private void responseLog(String uri, HttpStatus respStatus, Object responseBody) {
//		LOG.info("[ LIS-CORE REST Response ]");
//		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
//		LOG.info("request uri: {}", uri);
//		LOG.info("respStatus: {}", respStatus);
//		LOG.info("responseBody: {}", responseBody);
//		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
//	}
//	
//	private void errorLog(String uri, HttpStatus respStatus, Exception e) {
//		LOG.error("[ LIS-CORE REST Response ERROR ]");
//		LOG.error("--------------------------------------------------------------------------------------------------------------------------");
//		LOG.error("request uri: {}", uri);
//		LOG.error("respStatus: {}", respStatus);
//		LOG.error("--------------------------------------------------------------------------------------------------------------------------");
//		LOG.error("Exception: ", e);
//	}
	
}
