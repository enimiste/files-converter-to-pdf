package com.votek.pdfConverter.api;

import java.util.PriorityQueue;

import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

public interface PdfTransformer {
	/**
	 * 
	 * @param file
	 * @return
	 * @throws FilePdfTransformationException
	 */
	default FileResponse applyTransformations(PriorityQueue<PdfTransformation> transformations, FileResponse file,
			Configuration conf) throws FilePdfTransformationException {
		for (PdfTransformation t : transformations) {
			file = t.apply(file, conf);
		}

		return file;
	}

	/**
	 * 
	 * @return
	 */
	static PdfTransformer getDefault() {
		return new PdfTransformer() {
		};
	}

	/**
	 * 
	 * @author HP
	 *
	 */
	interface PdfTransformation extends Comparable<PdfTransformation> {
		FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException;

		int getPriority();

		default int compareTo(PdfTransformation o) {
			return o == null ? -1 : (this.getPriority() - o.getPriority());
		}
	}
}
