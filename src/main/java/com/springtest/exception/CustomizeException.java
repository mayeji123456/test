package com.springtest.exception;

public class CustomizeException extends RuntimeException{

	private static final long serialVersionUID = -7668964231630859946L;
	
	
	private String message;
	
	public CustomizeException(CustomizeErrorCode errorcode) {
		this.message=errorcode.getMessage();
	}
	public CustomizeException(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
