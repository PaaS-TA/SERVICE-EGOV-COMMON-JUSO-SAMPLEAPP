package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot 시작을 위한 Class
 * 
 * @author 안찬영
 * 
 * History
 * 2015.7.6 최초 Framework 구성시 작업
 */
@EnableAutoConfiguration
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    	
}