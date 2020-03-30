package com.kaizerwebdesign.hotelapp.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.kaizerwebdesign.hotelapp.rest")
public class RestResourceExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException exc){
		ErrorResponse error;
		if(exc.getMessage() == "403") {
			error = new ErrorResponse(HttpStatus.FORBIDDEN.value(),"ACCESS DENIED !",System.currentTimeMillis());
			return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
		}else {
			error = new ErrorResponse(HttpStatus.FORBIDDEN.value(),exc.getMessage(),System.currentTimeMillis());
			return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc){
		
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),exc.getMessage(),System.currentTimeMillis());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
}
