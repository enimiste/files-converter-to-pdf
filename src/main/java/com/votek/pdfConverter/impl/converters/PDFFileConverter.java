package com.votek.pdfConverter.impl.converters;

import java.util.Arrays;
import java.util.List;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.InvalidFileResponseException;
import com.votek.pdfConverter.api.exception.NotImplementedException;
import com.votek.pdfConverter.api.util.FileHelper;

public class PDFFileConverter implements FileToPdfConverter {

	@Override
	public boolean canHandle(FileData file) {
		return FileHelper.isPathExists(file) && FileHelper.hasExtension(file, "pdf");
	}

	@Override
	public List<FileResponse> handle(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		try {
			// It do nothing, just forword the pdf file to transformers
			return Arrays.asList(FileResponse.from(file.getInputFile(), -1));
		} catch (InvalidFileResponseException e) {
			throw new FileConverterException(file.getInputFile(), e);
		}
	}

	@Override
	public List<FileResponse> handle(List<FileData> files, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		// TODO
		throw new NotImplementedException();
	}

	@Override
	public String getName() {
		return "PDFFileConverter";
	}
}
