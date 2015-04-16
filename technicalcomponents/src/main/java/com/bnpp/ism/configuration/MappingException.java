package com.bnpp.ism.configuration;

public class MappingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8459806250225331778L;
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
