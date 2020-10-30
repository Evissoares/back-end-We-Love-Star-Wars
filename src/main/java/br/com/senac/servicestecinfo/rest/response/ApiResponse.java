package br.com.senac.servicestecinfo.rest.response;

import lombok.Getter;

@Getter
public class ApiResponse {
	private String message;
	private Object data;
	
	public ApiResponse() {
		
	}
	
	public ApiResponse(String message, Object data) {
		this.message = message;
		this.data = data;
		
	}
	
	public ApiResponse setMessage(String message) {
		
		this.message = message;
		return this;
	}
	
	public ApiResponse setData(Object data) {
		
		this.data = data;
		return this;
	}
}
