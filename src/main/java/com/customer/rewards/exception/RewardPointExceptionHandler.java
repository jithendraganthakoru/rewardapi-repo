package com.customer.rewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * To handle Exceptions Globally
 * 
 */

@ControllerAdvice
public class RewardPointExceptionHandler {
	
	/**
	 * 
	 * Handles all uncaught exceptions  
	 * 
	 * @return a ResponseEntity with HTTP 500 status and error message
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                         .body("An unexpected error occurred: " + ex.getMessage());
	}
	
	/**
	 * @param exception
	 * @return a ResponseEntity with Http 404 status and custom error msg as CustomerId Not Found
	 */
	@ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    } 
}