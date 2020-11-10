package com.votek.pdfConverter.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;
import com.votek.pdfConverter.api.exception.FileProcessingException;
import com.votek.pdfConverter.api.exception.InvalidFileResponseException;
import com.votek.pdfConverter.api.exception.NoConverterFoundException;

/**
 * 
 * @author HP
 *
 */
public abstract class AbstractPdfConverterEngine implements PdfConverterEngine {
	/**
	 * Checks the converters one after one to find the first one that can handle the
	 * file
	 * 
	 * @param file
	 * @param conf
	 * @return
	 * @throws FileNotFoundException
	 * @throws FileProcessingException
	 * @throws FileConverterException
	 */
	public List<FileResponse> convert(FileData file, Configuration conf)
			throws FileNotFoundException, NoConverterFoundException, FileProcessingException, FileConverterException {
		runProcessors(file, conf);
		List<FileResponse> results = runConverters(file, conf);
		results = runTransformations(file, conf, results);
		return results;
	}

	/**
	 * 
	 * @param file
	 * @param conf
	 * @param pdfs
	 * @return
	 * @throws FilePdfTransformationException
	 * @throws FileNotFoundException
	 * @throws FileConverterException
	 */
	protected List<FileResponse> runTransformations(FileData file, Configuration conf, List<FileResponse> pdfs)
			throws FilePdfTransformationException, FileNotFoundException, FileConverterException {
		List<FileResponse> results = new ArrayList<>();
		for (FileResponse res : pdfs) {
			res = getTransformer().applyTransformations(file.getTransformations(), res, conf);
			try {
				File outFile = new File(conf.getOutDir(), res.getOutputFile().getName());
				if (outFile.exists())
					outFile.delete();
				Files.copy(res.getOutputFile().toPath(), outFile.toPath());
				// Delete the old file
				if (res.getOutputFile().exists())
					res.getOutputFile().delete();
				results.add(FileResponse.from(outFile, res.getPagesNumber()));
			} catch (IOException | InvalidFileResponseException e) {
				throw new FileConverterException(res.getOutputFile(), e);
			}
		}

		return results;
	}

	/**
	 * 
	 * @param file
	 * @param conf
	 * @return
	 * @throws FileNotFoundException
	 * @throws FileConverterException
	 * @throws NoConverterFoundException
	 */
	protected List<FileResponse> runConverters(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException, NoConverterFoundException {
		List<FileResponse> results = new ArrayList<>();
		List<FileResponse> items = null;
		boolean handled = false;
		for (FileToPdfConverter converter : getConverters()) {
			if (converter.canHandle(file)) {
				items = converter.handle(file, conf);
				if (items != null)
					results.addAll(items);
				handled = true;
				break;
			}
		}
		if (!handled)
			throw new NoConverterFoundException(file.getInputFile());
		return results;
	}

	/**
	 * 
	 * @param file
	 * @param conf
	 * @throws FileNotFoundException
	 * @throws FileProcessingException
	 */
	protected void runProcessors(FileData file, Configuration conf)
			throws FileNotFoundException, FileProcessingException {
		// Run all processors on the file
		for (FileProcessor processor : conf.getProcessor()) {
			processor.process(file);
		}
	}
}
