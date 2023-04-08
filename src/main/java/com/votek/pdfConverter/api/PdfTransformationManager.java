package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.util.LinkedHashSet;

public interface PdfTransformationManager {
	/**
	 * @return
	 */
	static PdfTransformationManager newInstance() {
		return new PdfTransformationManager() {
		};
	}

	/**
	 * @param file
	 * @return
	 * @throws FilePdfTransformationException
	 */
	default FileResponse applyTransformations(LinkedHashSet<PdfTransformation> transformations, FileResponse file,
											  Configuration conf) throws FilePdfTransformationException {
		for (PdfTransformation t : transformations) {
			file = t.apply(file, conf);
		}

		return file;
	}

	/**
	 * @author HP
	 */
	interface PdfTransformation {
		FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException;
	}
}
