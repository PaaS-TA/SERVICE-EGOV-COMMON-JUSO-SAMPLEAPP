package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	static public ResponseEntity<String> send(String url, HttpEntity<String> entity, HttpMethod httpMethod) throws Exception {

		RestTemplate client = new RestTemplate();
		ResponseEntity<String> httpResponse=null;
		try {
			httpResponse = client.exchange(url, httpMethod, entity, String.class);		
		} catch (Exception e) {
			
			if(e.getMessage().equals("404 Not Found"))
			{
				throw new Exception("Server Not Found");
			}
			logger.error(e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		logger.info("Http Response");
		return httpResponse;
	}

}
