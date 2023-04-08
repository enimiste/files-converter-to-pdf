package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.util.LinkedHashSet;

public interface PdfTransformer {
	/**
	 *
	 * @return
	 */
	static PdfTransformer getDefault() {
		return new PdfTransformer() {
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
