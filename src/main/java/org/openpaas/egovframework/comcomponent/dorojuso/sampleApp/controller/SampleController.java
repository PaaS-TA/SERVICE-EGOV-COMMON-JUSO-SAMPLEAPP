package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;

import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.CloudFoundryConnector;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model.SampleVO;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model.TokenRequestVO;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model.TokenVO;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Sample 앱을 위한 Controller
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.6 최초게발
 */
@Controller
public class SampleController {

	@Autowired
	SampleService sampleService;
	
	/**
	 * "/"로 요청이 들어왔을 때의 처리
	 * 
	 * @param model	
	 * @return
	 */
    @RequestMapping("/")
    public String sample(Model model) {
    	
    	System.out.println("==== in Sample App ====");
    	
    	CloudFoundryConnector con = new CloudFoundryConnector();
    	ApplicationInstanceInfo instance_info = con.getApplicationInstanceInfo();
    	
    	System.out.println("=== ApplicationInstanceInfo ==========");
    	System.out.println("-- getAppId() : " + instance_info.getAppId());
    	System.out.println("-- getInstanceId() : " + instance_info.getInstanceId());
    	System.out.println("-- getProperties() : "+ instance_info.getProperties().toString());

    	List<String> uris = (List<String>)instance_info.getProperties().get("application_uris");
    	model.addAttribute("server_uri", uris.get(0));
    	
    	
    	for (Map<String, Object> item : (List<Map<String, Object>>)con.getServicesData()) {
    		
    		System.out.println("==> " + item.toString());
    		
        	Map<String, Object> service_info = (Map<String, Object>)item.get("credentials");

        	// 도로주소 Open API
        	if ("dorojuso".equals(item.get("name"))) {
    	    	model.addAttribute("api_uri", service_info.get("uri"));
    	    	model.addAttribute("api_username", service_info.get("username"));
    	    	model.addAttribute("api_password", service_info.get("password"));
    	    	model.addAttribute("api_consumerKey", service_info.get("prodConsumerKey"));
    	    	model.addAttribute("api_consumerSecret", service_info.get("prodConsumerSecret"));
    			
    		} else if ("dorojusodb".equals(item.get("name"))) {
    		// 도로명 주소 검색 서비스 (자체 DB)
    	    	model.addAttribute("db_uri", service_info.get("uri"));
    	    	model.addAttribute("db_username", service_info.get("username"));
    	    	model.addAttribute("db_password", service_info.get("password"));
    	    	model.addAttribute("db_consumerKey", service_info.get("prodConsumerKey"));
    	    	model.addAttribute("db_consumerSecret", service_info.get("prodConsumerSecret"));
    			
    		} else if ("dorojusodbmgt".equals(item.get("name"))) {
    		// 도로명 주소 관리 서비스 (자체DB)
    	    	model.addAttribute("mgt_uri", service_info.get("uri"));
    	    	model.addAttribute("mgt_username", service_info.get("username"));
    	    	model.addAttribute("mgt_password", service_info.get("password"));
    	    	model.addAttribute("mgt_consumerKey", service_info.get("prodConsumerKey"));
    	    	model.addAttribute("mgt_consumerSecret", service_info.get("prodConsumerSecret"));
    		}
    	}
    	
        return "app";
    }

    /**
     * Local환경에서 테스트 하기 위한 Path
     * 서버를 바라보지 않고 Local환경의 서버를 바라보게 함.
     * 
     * @param model
     * @return
     */
    @RequestMapping("/test")
    public String sampleLocalTest(Model model) {
    	
    	System.out.println("==== in Sample App (TEST) ====");

    	model.addAttribute("server_uri", "localhost:8080");

    	model.addAttribute("api_uri", "https://115.68.46.28:8243/dorojuso/1.0.0");
    	model.addAttribute("api_username", "apiuser");
    	model.addAttribute("api_password", "bddhkdlwm12#");
    	model.addAttribute("api_consumerKey", "p38L5MwRyVOZc2YcRThhmZApp50a");
    	model.addAttribute("api_consumerSecret", "H5n_fUovyfCrHsP0CGXRGr7cAOga");
    			
    	model.addAttribute("db_uri", "https://115.68.46.28:8243/dorojusodb/1.0.0");
    	model.addAttribute("db_username", "apiuser");
    	model.addAttribute("db_password", "bddhkdlwm12#");
    	model.addAttribute("db_consumerKey", "p38L5MwRyVOZc2YcRThhmZApp50a");
    	model.addAttribute("db_consumerSecret", "H5n_fUovyfCrHsP0CGXRGr7cAOga");
    			
    	model.addAttribute("mgt_uri", "https://115.68.46.28:8243/dorojusodbmgt/1.0.0");
    	model.addAttribute("mgt_username", "apiuser");
    	model.addAttribute("mgt_password", "bddhkdlwm12#");
    	model.addAttribute("mgt_consumerKey", "p38L5MwRyVOZc2YcRThhmZApp50a");
    	model.addAttribute("mgt_consumerSecret", "H5n_fUovyfCrHsP0CGXRGr7cAOga");

    	return "app";
    }
    
    /**
     * 환경 정보를 확인하기 위한 DEBUG 정보
     * 바인드가 정상적으로 이루어졌는지 확인 가능
     * 
     * @param model
     * @return
     */
    @RequestMapping("/vcap")
    public String VCAPTEST(Model model) {
    	
    	System.out.println("==== in Sample App ====");
    	
    	SampleVO sample = new SampleVO();
    	sample.setApiurl("API");
    	
    	CloudFoundryConnector con = new CloudFoundryConnector();
    	ApplicationInstanceInfo instance_info = con.getApplicationInstanceInfo();
    	
    	System.out.println("ApplicationInstanceInfo ==========");
    	System.out.println("-- getAppId() : " + instance_info.getAppId());
    	System.out.println("-- getInstanceId() : " + instance_info.getInstanceId());
    	System.out.println("-- getProperties() : "+ instance_info.getProperties().toString());

    	
    	List<ServiceInfo> infos = con.getServiceInfos();
    	for(ServiceInfo service_info : infos) {
    		
    		System.out.println("== getId() : " + service_info.getId());
    		System.out.println("== toString() : " + service_info.toString());

    	}
    	
    	System.out.println("== getServicesData() : " + con.getServicesData().toString());
    	//model.addAttribute("apiurl",  "http://test.com");
    	
        return "app";
    }
  
    /**
     * API 플랫폼에서 Token을 가져오기 위한 API
     * API 플랫폼에서 HTTPS 처리시 인증서 문제(공인인증서가 아닌경우)로 HTML에서 Token을 직접 가져오지를 못함.
     * SSL을 우회해서 Token정보를 가져오기 위해 서버에서 Token을 받아서 Client로 넘겨주고 있음.
     * 
     * @param requestVO	인증을 위한 사용자 정보
     * @param request	
     * @return	인증을 받은 토큰 정보
     */
    @RequestMapping(value="/token", method=RequestMethod.POST)
    public @ResponseBody TokenVO token(@RequestBody TokenRequestVO requestVO, HttpServletRequest request) {
    	
    	System.out.println("==== in token ====");
    	System.out.println("Authorization:" + request.getHeader("Authorization"));
    	System.out.println("Content-Type:" + request.getHeader("Content-type"));
    	System.out.println("request:" + requestVO.toString());
    	
    	// SSL 없이 통신을 위함.
    	TokenVO token = sampleService.getTokenWithoutSSL(request.getHeader("Authorization"), request.getHeader("Content-Type"), requestVO);    	
    	
        return token;
    }   
}