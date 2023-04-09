package com.votek.pdfConverter.impl.transformation;

import com.itextpdf.text.pdf.PdfReader;
import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfTransformation;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

public class PdfPageCountTransformation implements PdfTransformation {

	@Override
	public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
		PdfReader in = null;
		try {
			in = new PdfReader(file.getOutputFile().toURI().toURL());

			return FileResponse.from(file.getOutputFile(), in.getNumberOfPages());
		} catch (Exception e) {
			throw new FilePdfTransformationException(file.getOutputFile(), e);
		} finally {
			if (in != null)
				in.close();
		}
	}

}
