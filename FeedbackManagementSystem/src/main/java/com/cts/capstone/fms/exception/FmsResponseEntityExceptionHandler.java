package com.cts.capstone.fms.exception;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class FmsResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<FmsExceptionResponse> handleCommonException(Exception e, WebRequest request) {
		e.printStackTrace();
		FmsExceptionResponse fmsExceptionResponse = 
				new FmsExceptionResponse(e.getMessage(), request.getDescription(false), new Date());
		
		return new ResponseEntity<FmsExceptionResponse>(fmsExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		FmsExceptionResponse fmsExceptionResponse = 
				new FmsExceptionResponse("Validation Failed", ex.getBindingResult().getFieldError().getDefaultMessage(), new Date());
		
		return new ResponseEntity<>(fmsExceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
}
