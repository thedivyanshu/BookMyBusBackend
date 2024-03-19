package com.bookMyBus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(value = {NullUserException.class})
	public ResponseEntity<ExceptionObject> handleNullUserException(NullUserException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {UserAlreadyExistException.class})
	public ResponseEntity<ExceptionObject> handleUserAlreadyExistException(UserAlreadyExistException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {UserDoesnotExistException.class})
	public ResponseEntity<ExceptionObject> handleUserDoesnotExistException(UserDoesnotExistException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = {UserValidationException.class})
	public ResponseEntity<ExceptionObject> handleValidationException(UserValidationException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {PassengerNotFoundException.class})
	public ResponseEntity<ExceptionObject> handlePassengerError(PassengerNotFoundException e){
		return new ResponseEntity<ExceptionObject>(new ExceptionObject(e.getMessage(), HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
	}
	
	
	
}
