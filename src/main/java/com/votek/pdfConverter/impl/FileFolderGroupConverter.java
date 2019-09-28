package com.votek.pdfConverter.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileGroupConverter;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileUnGroupException;

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

		return Arrays.stream(file.getInputFile().listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.compareTo(".") != 0 && name.compareTo("..") != 0;
			}
		})).map(f -> {
			return file.copy(f);
		}).collect(Collectors.toList());
	}

	protected boolean isFileExists(File file) {
		return file.exists() && file.isDirectory();
	}

	@Override
	public String getName() {
		return "FileFolderGroupConverter";
	}
}
