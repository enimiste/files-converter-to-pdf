package com.votek.pdfConverter.impl.transformation;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PdfCopyTransformation implements PdfTransformation {
	protected File dest;

	public PdfCopyTransformation(File destination) {
		super();
		this.dest = destination;
	}

	@Override
	public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
		try {
			File outFile = new File(dest, file.getOutputFile().getName());
			if (outFile.getAbsolutePath().compareTo(file.getOutputFile().getAbsolutePath()) != 0)
				Files.copy(file.getOutputFile().toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return FileResponse.from(outFile, file.getPagesNumber());
		} catch (Exception e) {
			throw new FilePdfTransformationException(file.getOutputFile(), e);
		}
	}

}
