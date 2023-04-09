package com.votek.pdfConverter;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileData.Format;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfConverterEngine;
import com.votek.pdfConverter.impl.transformation.PdfCopyTransformation;
import com.votek.pdfConverter.impl.transformation.PdfPageCountTransformation;
import com.votek.pdfConverter.impl.transformation.PdfScaleTransformation;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * For testing purposes
 *
 * @author HP
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage :");
            System.out.println("java -jar program.jar  filePath  scale  format");
            System.out.println("-scale : double value in % (> 0). Ex : 100 for 100%, 200 for 200%");
            System.out.println("-format : one of these strings A0, A1, A2, A3, A4, A5, A6");
            System.out.println("Thanks !");
            return;
        }

        int n = args.length;

        String filePath = args[0];
        double scale = Double.parseDouble(args[1]);
        Format format = Format.valueOf(args[2]);

        run(filePath, scale, format);
    }

    public static void run(String path, double scale, Format format) {
        try {

            PdfConverterEngine engine = PdfConverterEngine.newInstance();
            // Ex : engine.addFileConverter(converter);
            Configuration conf = Configuration.of(new File("./tmp"), new File("./out"));
            // Ex : conf.addFileProcessor(processor);
            FileData fileData = FileData.of(new File(path), scale, format,
                    new LinkedHashSet<>(
                            Arrays.asList(
                                    new PdfCopyTransformation(conf.getTempDir()),
                                    // new PdfSkipPagesTransformation(1),
                                    // new PdfTakePagesTransformation(10),
                                    new PdfScaleTransformation(scale, format),
                                    new PdfPageCountTransformation())
                    ));
            List<FileResponse> files = engine.convert(fileData, conf);

            files.forEach(System.out::println);
            System.out.println("End");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
