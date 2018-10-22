package com.ln.domain;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class ReturnResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusText;
    private String statusMessage;
    
    public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

public static <T> ReturnResponse<T> getHttpStatusResponse(String message,
		HttpStatus status, T res) {
	ReturnResponse<T> returnResponse = new ReturnResponse<T>();
	returnResponse.setStatusCode(String.valueOf(status.value()));
	returnResponse.setStatusText(status.getReasonPhrase());
	returnResponse.setStatusMessage(message);
	return returnResponse;
}
}