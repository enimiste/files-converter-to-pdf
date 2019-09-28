package com.votek.pdfConverter.impl.converters;

import java.util.List;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.NotImplementedException;
import com.votek.pdfConverter.api.util.FileHelper;

public class PPTXFileConverter implements FileToPdfConverter {

	@Override
	public boolean canHandle(FileData file) {
		return FileHelper.isPathExists(file) && FileHelper.hasExtension(file, "ppt", "pptx");
	}

	@Override
	public List<FileResponse> handle(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		// TODO
		throw new NotImplementedException();
	}

	@Override
	public String getName() {
		return "PPTXFileConverter";
	}
}
