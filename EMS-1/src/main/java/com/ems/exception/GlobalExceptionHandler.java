package com.ems.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<MyErrorDetails> employeeException(EmployeeException exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_GATEWAY);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> exception(Exception exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_GATEWAY);
	}
	
}
