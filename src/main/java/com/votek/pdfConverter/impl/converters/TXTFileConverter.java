package com.votek.pdfConverter.impl.converters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.exception.NotImplementedException;
import com.votek.pdfConverter.api.util.FileHelper;

public class TXTFileConverter implements FileToPdfConverter {

	@Override
	public boolean canHandle(FileData file) {
		return FileHelper.isPathExists(file) && FileHelper.hasExtension(file, "txt");
	}

	@Override
	public List<FileResponse> handle(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		List<FileResponse> results = new ArrayList<>();
		BufferedReader inReader = null;
		Document pdf = null;
		try {
			inReader = new BufferedReader(new FileReader(file.getInputFile()));
			pdf = new Document(PageSize.A4);
			BaseFont monoscaped = BaseFont.createFont(BaseFont.COURIER, BaseFont.CP1252, BaseFont.EMBEDDED);
			Font font = new Font(monoscaped);
			font.setStyle(Font.NORMAL);
			font.setColor(BaseColor.BLACK);
			font.setSize(12);

			File outFile = new File(conf.getTempDir(),
					file.getInputFile().getName().toLowerCase().replace(".txt", ".pdf"));
			OutputStream out = new FileOutputStream(outFile);
			PdfWriter.getInstance(pdf, out);
			pdf.open();
			String line = null;
			Paragraph paragraphe = null;
			while ((line = inReader.readLine()) != null) {
				paragraphe = new Paragraph(line + "\n", font);
				paragraphe.setAlignment(Element.ALIGN_JUSTIFIED);
				pdf.add(paragraphe);
			}
			results.add(FileResponse.from(outFile, pdf.getPageNumber()));
			return results;
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException(file.getInputFile());
		} catch (Exception e) {
			throw new FileConverterException(file.getInputFile(), e);
		} finally {
			if (pdf != null && pdf.isOpen())
				pdf.close();
			if (inReader != null)
				try {
					inReader.close();
				} catch (IOException e) {
					throw new FileConverterException(file.getInputFile(), e);
				}
		}
	}

	@Override
	public List<FileResponse> handle(List<FileData> files, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		// TODO
		throw new NotImplementedException();
	}

	@Override
	public String getName() {
		return "TXTFileConverter";
	}
}
