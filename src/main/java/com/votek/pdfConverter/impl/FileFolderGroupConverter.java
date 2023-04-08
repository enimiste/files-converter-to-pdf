package com.votek.pdfConverter.impl;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileGroupConverter;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileUnGroupException;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * It handles paths to folders, so all the files inside the folder will be
 * converted
 * 
 * @author HP
 *
 */
public class FileFolderGroupConverter extends FileGroupConverter {

	public FileFolderGroupConverter(Set<FileToPdfConverter> converters) {
		super(converters);
	}

	@Override
	public boolean canHandle(FileData file) {
		return isFileExists(file.getInputFile());
	}

	@Override
	protected List<FileData> unGroup(FileData file, Configuration conf)
			throws FileNotFoundException, FileUnGroupException {
		if (!isFileExists(file.getInputFile()))
			throw new FileNotFoundException(file.getInputFile());

		return Arrays.stream(Objects.requireNonNull(file.getInputFile().listFiles((dir, name) -> name.compareTo(".") != 0 && name.compareTo("..") != 0))).map(file::copyWith).collect(Collectors.toList());
	}

	protected boolean isFileExists(File file) {
		return file.exists() && file.isDirectory();
	}

	@Override
	public String getName() {
		return "FileFolderGroupConverter";
	}
}
