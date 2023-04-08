package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileProcessingException;

public interface FileProcessor {
	/**
	 * Process the file at the given path and save it back
	 *
	 * @param file
	 */
	void process(FileData file) throws FileNotFoundException, FileProcessingException;
}
