package com.bnpp.ism.technicalcomponents.application.service.storage;

public class StorageException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7389128218391597705L;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
