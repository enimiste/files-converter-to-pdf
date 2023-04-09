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
import java.io.OutputStream;
import java.nio.file.Files;

public class PdfTakePagesTransformation implements PdfTransformation {
    protected int takePages;

    public PdfTakePagesTransformation(int takePages) {
        super();
        this.takePages = takePages <= 0 ? 1 : takePages;
    }

    @Override
    public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
        PdfReader in = null;
        OutputStream out;
        Document doc = null;
        PdfCopy writer;
        try {
            in = new PdfReader(file.getOutputFile().toURI().toURL());
            in.selectPages(String.format("1-%d", takePages));

            doc = new Document();

            out = Files.newOutputStream(new File(conf.getTempDir(), file.getOutputFile().getName()).toPath());
            writer = new PdfCopy(doc, out);
            writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);
            doc.open();

            int n = in.getNumberOfPages();
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
