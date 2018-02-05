package org.openpaas.egovframework.comcomponent.dorojuso.sampleApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API 처리 결과 메세제
 * 
 * @author 안찬영
 *
 * History
 * 2015.7.9 최초게발
 */
public class ResultMessage {

	/**
	 * 메세지
	 * 성공, 오류는 Status 코드로 Return됨
	 */
	private String message;

	public ResultMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
