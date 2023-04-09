package com.votek.pdfConverter.impl.transformation;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfTransformation;
import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PdfSkipPagesTransformation implements PdfTransformation {
    protected int pagesCount;

    public PdfSkipPagesTransformation(int pagesCount) {
        super();
        this.pagesCount = pagesCount <= 0 ? 1 : pagesCount;
    }

    @Override
    public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
        PdfReader in = null;
        OutputStream out = null;
        Document doc = null;
        PdfCopy writer = null;
        try {
            in = new PdfReader(file.getOutputFile().toURI().toURL());
            int n = in.getNumberOfPages();
            if (pagesCount > n)
                pagesCount = n;// To avoid a pdf without any page
            in.selectPages(String.format("%d-%d", pagesCount + 1, n + 1));// n+1 to avoid that pagesCount+1 be gt n

            doc = new Document();

            out = new FileOutputStream(new File(conf.getTempDir(), file.getOutputFile().getName()));
            writer = new PdfCopy(doc, out);
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            doc.open();

            n = in.getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                PdfImportedPage page = writer.getImportedPage(in, i);

                writer.addPage(page);
            }
            return file;
        } catch (Exception e) {
            throw new FilePdfTransformationException(file.getOutputFile(), e);
        } finally {
            if (doc != null && doc.isOpen())
                doc.close();
            if (in != null)
                in.close();
        }

    }

}
