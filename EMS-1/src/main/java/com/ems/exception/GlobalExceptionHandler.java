package com.ems.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EmployeeException.class)
	public ResponseEntity<MyErrorDetails> employeeException(EmployeeException exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return properStatusCode(exc, err);
		
	}
	
	@ExceptionHandler(DocumentException.class)
	public ResponseEntity<MyErrorDetails> exception(DocumentException exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return properStatusCode(exc, err);
		//return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SalaryException.class)
	public ResponseEntity<MyErrorDetails> exception(SalaryException exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return properStatusCode(exc, err);
		//return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LeaveException.class)
	public ResponseEntity<MyErrorDetails> exception(LeaveException exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return properStatusCode(exc, err);
		//return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> MethodArgumentNotValidException(MethodArgumentNotValidException ad,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(ad.getMessage());
		err.setDescription(wrq.getDescription(false));
		System.out.println("ma");

		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> NoHandlerFoundException(NoHandlerFoundException ad,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(ad.getMessage());
		err.setDescription(wrq.getDescription(false));
		System.out.println("nhe");
		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> exception(Exception exc,WebRequest wrq){
		
		MyErrorDetails err=new MyErrorDetails();
		err.setLocaldateTime(LocalDateTime.now());
		err.setMessage(exc.getMessage());
		err.setDescription(wrq.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<MyErrorDetails> properStatusCode(Exception exc, MyErrorDetails err){
		
		String[] errorMessageArray = exc.getMessage().split("\\*_");
		Integer length = errorMessageArray.length;
		
		if(length != 1) {
			err.setMessage(errorMessageArray[1]);
			if(errorMessageArray[0].compareToIgnoreCase("HttpStatus.NOT_FOUND")==0)return new ResponseEntity<MyErrorDetails>(err,HttpStatus.NOT_FOUND);
			else return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);
		}
		
		else return new ResponseEntity<MyErrorDetails>(err,HttpStatus.BAD_REQUEST);

	}
	
	
	
}
