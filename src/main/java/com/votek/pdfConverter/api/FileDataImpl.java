package com.votek.pdfConverter.api;

import java.io.File;
import java.util.LinkedHashSet;

class FileDataImpl implements FileData {

	protected File inputFile;
	protected double outputScale;
	protected Format outputFormat;
	protected PdfTransformation transformation;

	protected FileDataImpl(File inputFile, double outputScale, Format outputFormat,
						   LinkedHashSet<PdfTransformation> transformations) {
		super();
		this.inputFile = inputFile;
		this.outputScale = outputScale;
		this.outputFormat = outputFormat;
		this.transformation = transformations == null ? PdfTransformation.NO_OP : new PdfTransformation.Chain(transformations);
	}

	@Override
	public File getInputFile() {
		return inputFile;
	}

	@Override
	public double getOutputScale() {
		return outputScale;
	}

	@Override
	public Format getOutputFormat() {
		return outputFormat;
	}

	@Override
	public FileData copyWith(File newInputfile) {
		return new FileDataImpl(newInputfile, outputScale, outputFormat, new LinkedHashSet<>());
	}

	@Override
	public PdfTransformation getTransformation() {
		return transformation;
	}

}
