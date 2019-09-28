package com.votek.pdfConverter.impl.converters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfWriter;
import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.FileToPdfConverter;
import com.votek.pdfConverter.api.exception.FileConverterException;
import com.votek.pdfConverter.api.exception.FileNotFoundException;
import com.votek.pdfConverter.api.util.FileHelper;

public class ImageFileConverter implements FileToPdfConverter {

	@Override
	public boolean canHandle(FileData file) {
		return FileHelper.isPathExists(file)
				&& FileHelper.hasExtension(file, "png", "jpeg", "jpg", "gif", "bmp", "tiff");
	}

	@Override
	public List<FileResponse> handle(FileData file, Configuration conf)
			throws FileNotFoundException, FileConverterException {
		List<FileResponse> results = new ArrayList<>();
		Document pdf = null;
		try {

			Image image = Image.getInstance(file.getInputFile().getAbsolutePath(), true);
			pdf = new Document();
			float lm = pdf.leftMargin(), rm = pdf.rightMargin(), bm = pdf.bottomMargin(), tm = pdf.topMargin();

			Rectangle pageSize = getImageOptimalPageSize(image, lm, rm, bm, tm);
			pdf.setPageSize(pageSize);

			Rectangle imageFitRect = new Rectangle(Math.min(image.getWidth(), pageSize.getWidth()),
					Math.min(image.getHeight(), pageSize.getHeight()));// getImageFitRect(pageSize, lm, rm, bm, tm);

			image.scaleToFit(imageFitRect);

			String outFileName = file.getInputFile().getName().toLowerCase().replace(".png", ".pdf")
					.replace(".jpg", ".pdf").replace(".jpeg", ".pdf").replace(".gif", ".pdf").replace(".bmp", ".pdf")
					.replace(".tiff", ".pdf");

			File outFile = new File(conf.getTempDir(), outFileName);
			OutputStream out = new FileOutputStream(outFile);
			PdfWriter writer = PdfWriter.getInstance(pdf, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			;
			pdf.open();
			pdf.newPage();
			writer.getDirectContent().addImage(image, centerContentTransformation(pdf, image));
			results.add(FileResponse.from(outFile, pdf.getPageNumber()));
			return results;
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException(file.getInputFile());
		} catch (Exception e) {
			throw new FileConverterException(file.getInputFile(), e);
		} finally {
			if (pdf != null && pdf.isOpen())
				pdf.close();
		}
	}

	private Rectangle getImageOptimalPageSize(Image image, float leftMargin, float rightMargin, float bottomMargin,
			float topMargin) {
		List<Rectangle> rects = new ArrayList<>();
		rects.add(PageSize.A0);
		rects.add(PageSize.A1);
		rects.add(PageSize.A2);
		rects.add(PageSize.A4);
		rects.add(PageSize.A5);
		rects.add(PageSize.A6);
		rects.add(PageSize.A7);
		rects.add(PageSize.A8);
		rects.add(PageSize.A9);
		rects.add(PageSize.A10);
		rects.add(PageSize.A0.rotate());
		rects.add(PageSize.A1.rotate());
		rects.add(PageSize.A2.rotate());
		rects.add(PageSize.A4.rotate());
		rects.add(PageSize.A5.rotate());
		rects.add(PageSize.A6.rotate());
		rects.add(PageSize.A7.rotate());
		rects.add(PageSize.A8.rotate());
		rects.add(PageSize.A9.rotate());
		rects.add(PageSize.A10.rotate());

		// TODO : Find the most accurate to hold the image (Next step is to fit it only
		// inside margins)
		double w = image.getWidth();// + leftMargin + rightMargin;
		double h = image.getHeight();// + bottomMargin + topMargin;
		return rects.stream().filter(r -> r.getWidth() >= w && r.getHeight() >= h)
				.min(Comparator.<Rectangle>comparingDouble(r -> Math.abs((r.getWidth() - w) * (r.getHeight() - h))))
				.orElse(PageSize.A0);
	}

	/**
	 * 
	 * @param doc
	 * @param page
	 * @param scale
	 * @return
	 */
	private static AffineTransform centerContentTransformation(Document doc, Image image) {
		Rectangle docPageSize = doc.getPageSize();
		double dw = docPageSize.getWidth(), dh = docPageSize.getHeight();
		double iw = image.getWidth(), ih = image.getHeight();
		double deltaW = dw - iw, deltaH = dh - ih;
		double tx = deltaW / 2, ty = deltaH / 2;

		return AffineTransform.getTranslateInstance(tx, ty);
	}

	@Override
	public String getName() {
		return "PNGFileConverter";
	}
}
