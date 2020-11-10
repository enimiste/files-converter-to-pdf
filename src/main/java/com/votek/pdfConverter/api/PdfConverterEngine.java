package com.votek.pdfConverter.api;

import java.util.List;
import java.util.Set;

import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FileProcessingException;
import com.votek.pdfConverter.api.exception.NoConverterFoundException;

/**
 * 
 * @author HP
 *
 */
public interface PdfConverterEngine {

	Set<FileToPdfConverter> getConverters();

	void addFileConverter(FileToPdfConverter converter);

	/**
	 * 
	 * @param file
	 * @param conf
	 * @return
	 * @throws FileNotFoundException
	 * @throws NoConverterFoundException
	 * @throws FileProcessingException
	 * @throws FileConverterException
	 */
	List<FileResponse> convert(FileData file, Configuration conf)
			throws FileNotFoundException, NoConverterFoundException, FileProcessingException, FileConverterException;

	default PdfTransformer getTransformer() {
		return PdfTransformer.getDefault();
	}

	static PdfConverterEngine engine() {
		return new PdfConverterEngineImpl();
	}
}
