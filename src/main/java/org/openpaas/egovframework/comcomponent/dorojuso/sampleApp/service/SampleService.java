package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model.TokenRequestVO;
import org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model.TokenVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Token 정보를 가져오기 위한 Service
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.9 최초게발
 */
@Service
public class SampleService {

	/**
	 * Token을 가져온다.
	 * 
	 * @param authorization
	 * @param content_type
	 * @param requestVO
	 * @return
	 */
	public TokenVO getToken(String authorization, String content_type, TokenRequestVO requestVO) {
		TokenVO token = new TokenVO();
		
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", authorization);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//		HttpEntity<TokenRequestVO> entity = new HttpEntity<TokenRequestVO>(requestVO, headers);
		HttpEntity<String> entity = new HttpEntity<String>("grant_type="+requestVO.getGrant_type()+"&username="+requestVO.getUsername()+"&password="+requestVO.getPassword(), headers);
		
		ResponseEntity<String> response = client.exchange("https://115.68.46.28:8243/token", 
										HttpMethod.POST, 
										entity, 
										String.class);		

//		ResponseEntity<TokenVO> response = client.exchange("https://115.68.46.28:8243/token", 
//				HttpMethod.POST, 
//				entity, 
//				TokenVO.class);		

		if (response.getStatusCode() != HttpStatus.OK) {
			System.err.println("401 token failed. " + response.getStatusCode() + "," + response.getBody());
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode result = null;
		try {
			// Convert response body(string) to JSON Object
			result = mapper.readValue(response.getBody(), JsonNode.class);
			
		} catch (JsonParseException e) {
			System.err.println(e.getMessage());
		} catch (JsonMappingException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		token.setAccess_token(result.get("access_token").asText());
		
		System.out.println("status:"+response.getStatusCode());
		
		System.out.println("result:"+token);
		
		
		return token;
	}
	
	/**
	 * API 플랫폼에 SSL 없이 Token을 가져오기 위함
	 * 
	 * @param authorization
	 * @param content_type
	 * @param requestVO
	 * @return
	 */
	public TokenVO getTokenWithoutSSL(String authorization, String content_type, TokenRequestVO requestVO) {
		
		TokenVO token = new TokenVO();

		HttpClient client = createHttpClient_AcceptsUntrustedCerts();
		HttpPost post = new HttpPost("https://115.68.46.28:8243/token");
		
		post.setHeader("Authorization", authorization);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("grant_type", requestVO.getGrant_type()));
		urlParameters.add(new BasicNameValuePair("username", requestVO.getUsername()));
		urlParameters.add(new BasicNameValuePair("password", requestVO.getPassword()));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return null;
		}
		
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		System.out.println("Resonse: "+response.getStatusLine().getStatusCode());
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		do {
			String line = "";
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (line == null)  break;
			sb.append(line);
		} while (true);
		
		System.out.println("body:"+sb.toString());

		ObjectMapper mapper = new ObjectMapper();
//		TokenVO result = null;
		try {
			// Convert response body(string) to JSON Object
			token = mapper.readValue(sb.toString(), TokenVO.class);
			
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		System.out.println("result:"+token);
		
		return token;
	}
	
	public HttpClient createHttpClient_AcceptsUntrustedCerts() {
	    HttpClientBuilder b = HttpClientBuilder.create();
	 
	    // setup a Trust Strategy that allows all certificates.
	    //
	    SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			    public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			        return true;
			    }
			}).build();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    b.setSslcontext(sslContext);
	 
	    // don't check Hostnames, either.
	    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
	    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	 
	    // here's the special part:
	    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
	    //      -- and create a Registry, to register it.
	    //
	    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.getSocketFactory())
	            .register("https", sslSocketFactory)
	            .build();
	 
	    // now, we create connection-manager using our Registry.
	    //      -- allows multi-threaded use
	    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
	    b.setConnectionManager( connMgr);
	 
	    // finally, build the HttpClient;
	    //      -- done!
	    HttpClient client = b.build();
	    return client;
	}
}


