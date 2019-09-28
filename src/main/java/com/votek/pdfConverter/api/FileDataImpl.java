package com.votek.pdfConverter.api;

import java.io.File;
import java.util.PriorityQueue;

import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;

class FileDataImpl implements FileData {

	protected File inputFile;
	protected double outputScale;
	protected Format outputFormat;
	protected PriorityQueue<PdfTransformation> transformations;

	public FileDataImpl(File inputFile, double outputScale, Format outputFormat) {
		super();
		this.inputFile = inputFile;
		this.outputScale = outputScale;
		this.outputFormat = outputFormat;
		this.transformations = new PriorityQueue<>();
	}

	public FileDataImpl(File inputFile, double outputScale, Format outputFormat,
			PriorityQueue<PdfTransformation> transformations) {
		this(inputFile, outputScale, outputFormat);
		this.transformations = transformations;
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
	public FileData copy(File newInputfile) {
		return new FileDataImpl(newInputfile, outputScale, outputFormat);
	}

	@Override
	public PriorityQueue<PdfTransformation> getTransformations() {
		return transformations;
	}

}
