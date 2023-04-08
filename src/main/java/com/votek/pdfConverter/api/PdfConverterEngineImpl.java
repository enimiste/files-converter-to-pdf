package com.votek.pdfConverter.api;

import com.votek.pdfConverter.impl.FileFolderGroupConverter;
import com.votek.pdfConverter.impl.FileZipGroupConverter;
import com.votek.pdfConverter.impl.converters.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author HP
 *
 */
class PdfConverterEngineImpl extends AbstractPdfConverterEngine {
	protected Set<FileToPdfConverter> converters;

	protected PdfConverterEngineImpl() {
		converters = new HashSet<>();
		converters.add(new DOCXFileConverter());
		converters.add(new PPTXFileConverter());
		converters.add(new XLSXFileConverter());
		converters.add(new PDFFileConverter());
		converters.add(new ImageFileConverter());
		converters.add(new TXTFileConverter());
		converters.add(new FileFolderGroupConverter(converters));
		converters.add(new FileZipGroupConverter(converters));
	}

	@Override
	public Set<FileToPdfConverter> getConverters() {
		return converters;
	}

	@Override
	public void addFileConverter(FileToPdfConverter converter) {
		converters.add(converter);
	}

}
