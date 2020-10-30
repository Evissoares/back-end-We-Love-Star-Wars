package br.com.senac.servicestecinfo.rest.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtil{
	public static ResponseEntity<ApiResponse> okResponseEntity (String message){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message,null),HttpStatus.OK);
	}
	
	public static ResponseEntity<ApiResponse> okResponseEntity (String message, Object response){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message,response),HttpStatus.OK);
	}
	
	public static ResponseEntity<ApiResponse> unprocessResponseEntity(String message, Object response){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, response),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	public static ResponseEntity<ApiResponse> createResponseEntity(String message, Object response){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, response),HttpStatus.CREATED);
	}
	public static ResponseEntity<ApiResponse> notResponseEntity(String message, Object response){
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(message, response),HttpStatus.NOT_FOUND);
	}
	
}
