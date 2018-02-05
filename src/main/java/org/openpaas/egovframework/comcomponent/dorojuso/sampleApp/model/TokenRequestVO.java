package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model;

/**
 * 인증 요청을 위한 사용자 정보
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.9 최초게발
 */
public class TokenRequestVO {

	/**
	 * 인증 방식
	 */
	private String grant_type;
	/**
	 * 사용자 ID
	 */
	private String username;
	/**
	 * 비밀번호
	 */
	private String password;
	
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
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
	@Override
	public String toString() {
		return "TokenRequestVO [grant_type=" + grant_type + ", username="
				+ username + ", password=" + password + "]";
	}
	
	
}
