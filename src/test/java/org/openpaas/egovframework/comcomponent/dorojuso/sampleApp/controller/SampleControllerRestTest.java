package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.common.HttpClientUtils;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.common.JsonUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import ch.qos.logback.core.net.server.Client;

import com.fasterxml.jackson.databind.JsonNode;


public class SampleControllerRestTest {
	
	private static Properties prop = new Properties();
	
	@BeforeClass
	public static void init() {
		
		System.out.println("== Started test DoroJuso API ==");

		// Initialization
		// Get properties information
		String propFile = "test.properties";
 
		InputStream inputStream = SampleControllerRestTest.class.getClassLoader().getResourceAsStream(propFile);
		
		try {
			prop.load(inputStream);
	 	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	 		System.err.println(e);
	 	}		
	}

	@AfterClass
	public static void deleteTestData() {
		HttpHeaders headers = new HttpHeaders();	
		headers.set("Accept", "application/json");
		
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		ResponseEntity<String> response = null;

		boolean bException = false;
		
		try {
			
			String url = prop.getProperty("test_base_protocol") + prop.getProperty("test_base_url") + prop.getProperty("dorojuso_manager_path");
			url += "/" + prop.getProperty("building_code");
			
			response = HttpClientUtils.send(url, entity, HttpMethod.DELETE);

		} catch (Exception ex) {
			System.err.println("After class exception:" + ex.getMessage());
		}
	}
	
	/**
	 * 도로 주소 가져오기가 잘되는지 확인
	 * - JSON 파싱이 잘되는지 확인함
	 */
	@Test	
	public void getDoroJusoInfoTest_normalJSON() {
		
		System.out.println("Start - normal JSON");
		
		HttpHeaders headers = new HttpHeaders();	
		headers.set("Accept", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>("", headers);
		ResponseEntity<String> response = null;

		boolean bException = false;
		
		try {
			
			String url = prop.getProperty("test_base_protocol") + prop.getProperty("test_base_url") + prop.getProperty("dorojuso_manager_path");
			url += "/10/10/강남대로10";
			
			response = HttpClientUtils.send(url, entity, HttpMethod.GET);

		} catch (Exception ex) {
			
			assertFalse("exception:" + ex.getMessage(), true);
			bException = true;
			
		}
		
		if (!bException) {
			try {
				JsonNode json = JsonUtils.convertToJson(response);
			} catch (Exception e) {
				assertFalse("exception (JSON convert):" + e.getMessage(), true);
				bException = true;
			}			
		}
		
		if (!bException) assertTrue("OK", true);

		System.out.println("End - normal JSON");
	}

}
