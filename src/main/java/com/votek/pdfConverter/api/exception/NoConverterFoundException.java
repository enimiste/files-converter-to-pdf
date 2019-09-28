package com.votek.pdfConverter.api.exception;

import java.io.File;

public class NoConverterFoundException extends FileConverterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoConverterFoundException(File file) {
		super(file);
	}

}
