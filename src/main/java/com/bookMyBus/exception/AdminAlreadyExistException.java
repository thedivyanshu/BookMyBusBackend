package com.bookMyBus.exception;

public class AdminAlreadyExistException extends RuntimeException {
		
	
	private static final long serialVersionUID = 1L;

	public AdminAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AdminAlreadyExistException(String message) {
		super(message);
		
	}

	
	
}
