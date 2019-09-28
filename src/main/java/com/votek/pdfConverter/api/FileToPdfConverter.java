package com.votek.pdfConverter.api;

import java.util.ArrayList;
import java.util.List;

import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;

public interface FileToPdfConverter {
	/**
	 * The implementing class should returns true if the converter can handle the
	 * input file
	 * 
	 * @param file
	 * @return
	 */
	boolean canHandle(FileData file);

	List<FileResponse> handle(FileData file, Configuration conf) throws FileNotFoundException, FileConverterException;

	default List<FileResponse> handle(List<FileData> files, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		List<FileResponse> result = new ArrayList<>();
		for (FileData file : files) {
			result.addAll(this.handle(file, conf));
		}
		return result;
	}

	String getName();
}
