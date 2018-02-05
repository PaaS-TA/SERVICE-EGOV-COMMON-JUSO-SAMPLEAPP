package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.common;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class) ;
	
	static public JsonNode convertToJson(ResponseEntity<String> httpResponse) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = null;
		try {
			json = mapper.readValue(httpResponse.getBody(), JsonNode.class);
		} catch (JsonParseException e) {
			throw new Exception(e.getMessage());
		} catch (JsonMappingException e) {
			throw new Exception(e.getMessage());
		} catch (IOException e) {
			throw new Exception(e.getMessage());
		} catch (Exception e){
			logger.warn("Json Convert: Exception");
			throw new Exception(e.getMessage());
		}
		
		logger.info("Json Convert: Complete");
		
		return json;
	}
	
}
