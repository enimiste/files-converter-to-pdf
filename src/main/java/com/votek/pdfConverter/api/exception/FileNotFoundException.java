package com.votek.pdfConverter.api.exception;

import java.io.File;

public class FileNotFoundException extends FileConverterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotFoundException(File file) {
		super(file);
	}

}
