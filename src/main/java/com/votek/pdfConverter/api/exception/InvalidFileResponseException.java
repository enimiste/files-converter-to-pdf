package com.votek.pdfConverter.api.exception;

public class InvalidFileResponseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidFileResponseException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFileResponseException(String message) {
		super(message);
	}

}
