package com.votek.pdfConverter.api.exception;

import java.io.File;

public class FileProcessingException extends FileConverterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileProcessingException(File file) {
		super(file);
	}
}
