package com.votek.pdfConverter.impl;

import java.util.List;
import java.util.Set;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileGroupConverter;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileUnGroupException;
import com.votek.pdfConverter.api.exception.NotImplementedException;
import com.votek.pdfConverter.api.util.FileHelper;

public class FileZipGroupConverter extends FileGroupConverter {

	public FileZipGroupConverter(Set<FileToPdfConverter> converters) {
		super(converters);
	}

	@Override
	public boolean canHandle(FileData file) {
		return FileHelper.isPathExists(file) && file.getInputFile().isFile() && FileHelper.hasExtension(file, "zip");
	}

	@Override
	protected List<FileData> unGroup(FileData file, Configuration conf)
			throws FileNotFoundException, FileUnGroupException {
		if (!FileHelper.isPathExists(file))
			throw new FileNotFoundException(file.getInputFile());
		// TODO
		throw new NotImplementedException();
	}

	@Override
	public String getName() {
		return "FileZipGroupConverter";
	}
}
