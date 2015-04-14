package com.bnpp.ism.technicalcomponents.application.service.component;

public class ComponentException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8733159281971009934L;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
