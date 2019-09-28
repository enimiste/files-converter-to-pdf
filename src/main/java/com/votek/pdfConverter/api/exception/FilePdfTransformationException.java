package com.votek.pdfConverter.api.exception;

import java.io.File;

public class FilePdfTransformationException extends FileConverterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FilePdfTransformationException(File file) {
		super(file);
	}

	public FilePdfTransformationException(File file, Throwable cause) {
		super(file, cause);
	}

}
