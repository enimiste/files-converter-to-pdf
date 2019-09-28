package com.votek.pdfConverter.api;

import java.io.File;

import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.InvalidFileResponseException;

/**
 * This interface holds pointer to PDF files only.
 * 
 * @author HP
 *
 */
public interface FileResponse {
	File getOutputFile();

	int getPagesNumber();

	static FileResponse from(File file, int pagesNumber) throws FileNotFoundException, InvalidFileResponseException {
		return new FileResponseImpl(file, pagesNumber);
	}
}
