package com.votek.pdfConverter.impl.transformation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

public class PdfCopyTransformation implements PdfTransformation {
	protected int priority;
	protected File dest;

	public PdfCopyTransformation(File destination, int priority) {
		super();
		this.priority = priority;
		this.dest = destination;
	}

	@Override
	public int getPriority() {
		return priority;
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
