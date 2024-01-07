package com.agcltr.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<String> handleCustomFeignException(CustomFeignException ex) {
        // Handle the custom Feign exception
		System.out.println("A");
        int status = ex.getStatus();
        String responseBody = ex.getResponseBody();
        return ResponseEntity.status(status).body("Feign Error: " + responseBody);
    }
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(OrderNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
