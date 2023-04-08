package com.votek.pdfConverter.impl.transformation;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData.Format;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfTransformer.PdfTransformation;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Scaling up or down is related to changing paper format So it needs to be
 * handled in one class
 * 
 * @author HP
 *
 */
public class PdfScaleTransformation implements PdfTransformation {
	protected Format format;
	protected double scale;
	protected static Map<Format, Rectangle> pageSizes = new HashMap<>();

	public PdfScaleTransformation(double scale, Format format) {
		super();
		this.scale = Math.max(1, Math.abs(scale));
		this.format = format;

		pageSizes.put(Format.A0, PageSize.A0);
		pageSizes.put(Format.A1, PageSize.A1);
		pageSizes.put(Format.A2, PageSize.A2);
		pageSizes.put(Format.A3, PageSize.A3);
		pageSizes.put(Format.A4, PageSize.A4);
		pageSizes.put(Format.A5, PageSize.A5);
		pageSizes.put(Format.A6, PageSize.A6);
	}

	@Override
	public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
		PdfReader in = null;
		OutputStream out = null;
		Document doc = null;
		PdfWriter writer = null;
		PdfImportedPage page = null;
		PdfContentByte canvas = null;
		try {
			in = new PdfReader(file.getOutputFile().toURI().toURL());

			Rectangle outSize = pageSize(format);
			// Keep document structure (Portrait or Landscap)
			Rectangle inSize = in.getPageSizeWithRotation(1);
			if (inSize.getWidth() > inSize.getHeight() && outSize.getWidth() < outSize.getHeight()) {
				outSize = new Rectangle(outSize.getHeight(), outSize.getWidth(), inSize.getRotation());
			}

			doc = new Document(inSize);
			File outFile = new File(conf.getTempDir(), file.getOutputFile().getName().toLowerCase().replace(".pdf",
					String.format("-%.2f-%s.pdf", scale, format)));
			out = new FileOutputStream(outFile);
			writer = PdfWriter.getInstance(doc, out);
			writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
			doc.open();
			canvas = writer.getDirectContent();
			int n = in.getNumberOfPages();
			for (int i = 1; i <= n; i++) {
				page = writer.getImportedPage(in, i);
				doc.newPage();

				AffineTransform scaleTr = AffineTransform.getScaleInstance(scale / 100, scale / 100);
				AffineTransform transTr = centerContentTransformation(doc, page, scale);

				scaleTr.concatenate(transTr);
				canvas.addTemplate(page, scaleTr);
			}

			return FileResponse.from(outFile, doc.getPageNumber());
		} catch (Exception e) {
			throw new FilePdfTransformationException(file.getOutputFile(), e);
		} finally {
			if (doc != null && doc.isOpen())
				doc.close();
			if (in != null)
				in.close();
		}
	}

	/**
	 *
	 * @param doc
	 * @param page
	 * @param scale
	 * @return
	 */
	private static AffineTransform centerContentTransformation(Document doc, PdfImportedPage page, double scale) {
		scale = scale / 100;
		Rectangle docPageSize = doc.getPageSize();
		double dw = docPageSize.getWidth(), dh = docPageSize.getHeight();
		double pw = page.getWidth(), ph = page.getHeight();
		double deltaW = dw - pw * scale, deltaH = dh - ph * scale;
		double tx = deltaW / 2, ty = deltaH / 2;

		return AffineTransform.getTranslateInstance(tx / scale, ty / scale);
	}

	/**
	 *
	 * @param format
	 * @return
	 */
	private static Rectangle pageSize(Format format) {
		return pageSizes.get(format);
	}

}
