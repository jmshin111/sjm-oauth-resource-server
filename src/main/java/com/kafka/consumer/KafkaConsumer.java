package com.kafka.consumer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.service.impl.CommonRestClient;
import com.rest.service.CommonClientService;

@Controller
public class KafkaConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumer.class);
	
	/** RestClient */
	@Resource(name="commonRestClient")
	private CommonRestClient restClient = null;
	
	public CommonRestClient getRestClient() {
		return restClient;
	}

	public void setRestClient(CommonRestClient restClient) {
		this.restClient = restClient;
	}
	
	@KafkaListener(topics = "test")
	public void listener(String message) {
		
		String result = "Foo Received Message in group : " + message;
	
		// 1. REST 호출을 위한 정보를 생성한다.
		// 1) header 정보를 처리한다.
		// - headers 정보를 이용해서 httpHeaders 정보를 생성
		HttpHeaders httpHeaders = new HttpHeaders();
//		if (headers != null && headers.size() > 0) {
//			// headers 필드에 설정되어 있는 주요 필드들에 대해서 처리한다.
//			for (String key : headers.keySet()) {
//				httpHeaders.add(key, ConvertUtil.convertToString(headers.get(key)));
//			}
//		}
		
		// 2) HttpEntity 정보와 url을 생성한다.
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
		httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		// GET 방식인 경우 params이 있는 경우 url에 queryParam으로 추가하고 비어있는 body를 생성한다.
		HttpEntity reqEntity = null;
//		if (method == HttpMethod.GET) {		
//			reqEntity = new HttpEntity<String>(httpHeaders);
//		} else {
//			String body = ConvertUtil.convertHaeObjectToJson(params);
//			reqEntity = new HttpEntity<String>(body, httpHeaders);
//		}
		String body = "";
		reqEntity = new HttpEntity<String>(body, httpHeaders);
		
		String url = "";
		String apiPath = "";
		HttpMethod method = HttpMethod.POST;
		
		// - API URL을 빌드해서 생성한다.
		String uri = UriComponentsBuilder
				.fromHttpUrl(url)
				.path(apiPath)
				.build()
				.toString();
		
		
		// 2. RestTemplate를 이용해서 API를 요청한다.
		HttpStatus respStatus = null;
		String respBody = null;
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		
		try {
			
			// Call Rest API
			/******************************************************************************************************/
			requestLog(request.getRequestURI(), uri, method, reqEntity.getHeaders(), reqEntity.getBody());
//			ResponseEntity<String> respEntity = restClient.exchange(uri, method, reqEntity, String.class, uriVars);

			ResponseEntity<String> respEntity = restClient.exchange(uri, method, reqEntity, String.class);
			/******************************************************************************************************/
			
			// 예외 없이 정상적으로 처리 된 경우
			/******************************************************************************************************/
			respStatus = respEntity.getStatusCode();
			responseLog(request.getRequestURI(), respStatus, respEntity.getBody());
			/******************************************************************************************************/
			
			if (respEntity.hasBody()) {	// body 데이터가 존재하는 경우
				respBody = respEntity.getBody();
			}
			
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			respStatus = e.getStatusCode();
			errorLog(request.getRequestURI(), respStatus, e);
		} catch (UnknownHttpStatusCodeException e){
			respStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			errorLog(request.getRequestURI(), respStatus, e);
		} catch (Exception e) {
			respStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			errorLog(request.getRequestURI(), respStatus, e);
		}		
		
		LOG.info(message);
	}
	
	private void requestLog(String uri, String liscoreUri, HttpMethod method, HttpHeaders headers, Object requestBody) {
		LOG.info("[ LIS-CORE REST Request ]");
		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
		LOG.info("request uri: {}", uri);
		LOG.info("lis-core uri: {}", liscoreUri);
		LOG.info("method: {}", method);
		LOG.info("requestHeaders: {}", headers);
		LOG.info("requestBody: {}", requestBody);
		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
	}
	
	private void responseLog(String uri, HttpStatus respStatus, Object responseBody) {
		LOG.info("[ LIS-CORE REST Response ]");
		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
		LOG.info("request uri: {}", uri);
		LOG.info("respStatus: {}", respStatus);
		LOG.info("responseBody: {}", responseBody);
		LOG.info("--------------------------------------------------------------------------------------------------------------------------");
	}
	
	private void errorLog(String uri, HttpStatus respStatus, Exception e) {
		LOG.error("[ LIS-CORE REST Response ERROR ]");
		LOG.error("--------------------------------------------------------------------------------------------------------------------------");
		LOG.error("request uri: {}", uri);
		LOG.error("respStatus: {}", respStatus);
		LOG.error("--------------------------------------------------------------------------------------------------------------------------");
		LOG.error("Exception: ", e);
	}
	
}
