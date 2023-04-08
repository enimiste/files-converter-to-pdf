package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;

import java.io.File;
import java.util.LinkedHashSet;

public interface FileData {
	File getInputFile();

	double getOutputScale();

	Format getOutputFormat();

	/**
	 * @param inputFile
	 * @param scale
	 * @param format
	 * @param transformations
	 * @return
	 */
	static FileData of(File inputFile, double scale, Format format, LinkedHashSet<PdfTransformation> transformations) {
		return new FileDataImpl(inputFile, scale, format, transformations);
	}

	LinkedHashSet<PdfTransformation> getTransformations();

	/**
	 * Create a copy of this instance and override the inputFile with the new value
	 *
	 * @param newInputfile
	 * @return
	 */
	FileData copyWith(File newInputfile);

	enum Format {
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
