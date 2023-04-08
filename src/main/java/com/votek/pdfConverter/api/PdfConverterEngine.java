package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileProcessingException;
import com.votek.pdfConverter.api.exception.NoConverterFoundException;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author HP
 *
 */
public interface PdfConverterEngine {

	Set<FileToPdfConverter> getConverters();

	void addFileConverter(FileToPdfConverter converter);

	static PdfConverterEngine newInstance() {
		return new PdfConverterEngineImpl();
	}

	default PdfTransformer getTransformer() {
		return PdfTransformer.getDefault();
	}

	/**
	 * @param file input resource information
	 * @param conf global configuration
	 * @return returns a list of all converted resources (grouped resources like zip files will be flatted)
	 * @throws FileNotFoundException     resource not found in the specified path
	 * @throws NoConverterFoundException no matching converter found for the given resource
	 * @throws FileProcessingException   error occurred while processing the given resource
	 * @throws FileConverterException    error occurred while converting the given resource
	 */
	List<FileResponse> convert(FileData file, Configuration conf)
			throws FileNotFoundException, NoConverterFoundException, FileProcessingException, FileConverterException;
}
