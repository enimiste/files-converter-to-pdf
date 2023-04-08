package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;

import java.io.File;
import java.util.LinkedHashSet;

class FileDataImpl implements FileData {

	protected File inputFile;
	protected double outputScale;
	protected Format outputFormat;
	protected LinkedHashSet<PdfTransformation> transformations;

	protected FileDataImpl(File inputFile, double outputScale, Format outputFormat,
						   LinkedHashSet<PdfTransformation> transformations) {
		super();
		this.inputFile = inputFile;
		this.outputScale = outputScale;
		this.outputFormat = outputFormat;
		this.transformations = transformations == null ? new LinkedHashSet<>() : transformations;
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
	public LinkedHashSet<PdfTransformation> getTransformations() {
		return transformations;
	}

}
