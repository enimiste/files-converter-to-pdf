package com.votek.pdfConverter.api.exception;

import java.io.File;

public class FileConverterException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected File file;

	public FileConverterException(File file) {
		super();
		this.file = file;
	}

	public FileConverterException(File file, String message, Throwable cause) {
		super(message, cause);
		this.file = file;
	}

	public FileConverterException(File file, String message) {
		super(message);
		this.file = file;
	}

	public FileConverterException(File file, Throwable cause) {
		super(cause);
		this.file = file;
	}

	public File getFile() {
		return file;
	}
}
