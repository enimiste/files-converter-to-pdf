package com.votek.pdfConverter.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileUnGroupException;

public abstract class FileGroupConverter implements FileToPdfConverter {

	protected Set<FileToPdfConverter> converters;

	public FileGroupConverter(Set<FileToPdfConverter> converters) {
		this.converters = converters == null ? (new HashSet<>()) : converters;
		this.converters.add(this);
	}

	@Override
	public List<FileResponse> handle(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		List<FileResponse> result = new ArrayList<>();
		if (canHandle(file)) {
			List<FileData> files = unGroup(file, conf);
			for (FileData f : files) {
				for (FileToPdfConverter conv : converters) {
					if (conv.canHandle(f)) {
						result.addAll(conv.handle(f, conf));
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * UnGroup a packed file or directory and returns many files to process
	 * 
	 * @param file
	 * @return
	 */
	protected abstract List<FileData> unGroup(FileData file, Configuration conf)
			throws FileNotFoundException, FileUnGroupException;
}
