package com.votek.pdfConverter.api;

import java.util.HashSet;
import java.util.Set;

import com.votek.pdfConverter.impl.FileFolderGroupConverter;
import com.votek.pdfConverter.impl.FileZipGroupConverter;
import com.votek.pdfConverter.impl.converters.DOCXFileConverter;
import com.votek.pdfConverter.impl.converters.ImageFileConverter;
import com.votek.pdfConverter.impl.converters.PDFFileConverter;
import com.votek.pdfConverter.impl.converters.PPTXFileConverter;
import com.votek.pdfConverter.impl.converters.TXTFileConverter;
import com.votek.pdfConverter.impl.converters.XLSXFileConverter;

/**
 * 
 * @author HP
 *
 */
class PdfConverterEngineImpl extends AbstractPdfConverterEngine {
	protected Set<FileToPdfConverter> converters;

	public PdfConverterEngineImpl() {
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
