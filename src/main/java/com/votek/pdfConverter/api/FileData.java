package com.votek.pdfConverter.api;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;
import com.votek.pdfConverter.impl.transformation.PdfPageCountTransformation;
import com.votek.pdfConverter.impl.transformation.PdfScaleTransformation;

public interface FileData {
	File getInputFile();

	double getOutputScale();

	Format getOutputFormat();

	PriorityQueue<PdfTransformation> getTransformations();

	/**
	 * Create a copy of this instance and override the inputFile with the new value
	 * 
	 * @param file
	 * @return
	 */
	FileData copy(File newInputfile);

	/**
	 * 
	 * @param inputFile
	 * @param scale
	 * @param format
	 * @return
	 */
	static FileData from(File inputFile, double scale, Format format) {
		return new FileDataImpl(inputFile, scale, format, new PriorityQueue<>(
				Arrays.asList(new PdfScaleTransformation(scale, format, 10), new PdfPageCountTransformation(20))));
	}

	/**
	 * 
	 * @param inputFile
	 * @param scale
	 * @param format
	 * @param transformations
	 * @return
	 */
	static FileData from(File inputFile, double scale, Format format, List<PdfTransformation> transformations) {
		return new FileDataImpl(inputFile, scale, format, new PriorityQueue<>(transformations));
	}

	public static enum Format {
		A0("A0"), A1("A1"), A2("A2"), A3("A3"), A4("A4"), A5("A5"), A6("A6");

		private final String format;

		Format(String f) {
			this.format = f;
		}

		public boolean equalsName(String n) {
			return format.contentEquals(n);
		}

		@Override
		public String toString() {
			return format;
		}
	}
}
