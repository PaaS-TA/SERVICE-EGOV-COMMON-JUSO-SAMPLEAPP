package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model;

/**
 * VCAP 정보를 확인하기 위한 객체
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.9 최초게발
 */
public class SampleVO {

	private String apiurl;
	private String client_id;
	private String client_password;
	private String username;
	private String password;
	private String tokenurl;
	public String getApiurl() {
		return apiurl;
	}
	public void setApiurl(String apiurl) {
		this.apiurl = apiurl;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_password() {
		return client_password;
	}
	public void setClient_password(String client_password) {
		this.client_password = client_password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTokenurl() {
		return tokenurl;
	}
	public void setTokenurl(String tokenurl) {
		this.tokenurl = tokenurl;
	}
	@Override
	public String toString() {
		return "SampleVO [apiurl=" + apiurl + ", client_id=" + client_id
				+ ", client_password=" + client_password + ", username="
				+ username + ", password=" + password + ", tokenurl="
				+ tokenurl + "]";
	}	
	
}
