package com.votek.pdfConverter.api.exception;

import java.io.File;

public class FileUnGroupException extends FileConverterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUnGroupException(File file) {
		super(file);
	}
}
