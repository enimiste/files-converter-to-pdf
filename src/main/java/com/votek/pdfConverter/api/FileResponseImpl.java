package com.votek.pdfConverter.api;

import java.io.File;

import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.InvalidFileResponseException;
import com.votek.pdfConverter.api.util.FileHelper;

class FileResponseImpl implements FileResponse {
	protected File output;
	protected int pagesNumber;

	public FileResponseImpl(File output, int pagesNumber) throws FileNotFoundException, InvalidFileResponseException {
		super();
		this.output = output;
		this.pagesNumber = pagesNumber;

		if (!FileHelper.isPathExists(output))
			throw new FileNotFoundException(output);
		if (!FileHelper.hasExtension(output, "pdf"))
			throw new InvalidFileResponseException("FileResponse output file should be a PDF file");
	}

	@Override
	public File getOutputFile() {
		return output;
	}

	@Override
	public int getPagesNumber() {
		return pagesNumber;
	}

	@Override
	public String toString() {
		return String.format("FileResponse(%s,%d)", output.getName(), pagesNumber);
	}
}
