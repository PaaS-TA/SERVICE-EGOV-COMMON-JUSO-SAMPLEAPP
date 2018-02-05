package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model;

/**
 * 인증 완료후 Token 정보
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.9 최초게발
 */
public class TokenVO {
	
	/**
	 * 범위
	 */
	private String scope;
	/**
	 * Token 유형
	 */
	private String token_type;
	/**
	 * 유효시간
	 */
	private long expires_in;
	/**
	 * Refresh Token
	 */
	private String refresh_token;
	/**
	 * Access Token
	 */
	private String access_token;
	
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public long getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	@Override
	public String toString() {
		return "TokenVO [scope=" + scope + ", token_type=" + token_type
				+ ", expires_in=" + expires_in + ", refresh_token="
				+ refresh_token + ", access_token=" + access_token + "]";
	}
	
	
}
