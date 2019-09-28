package com.votek.pdfConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.votek.pdfConverter.api.Configuration;
import com.votek.pdfConverter.api.FileData;
import com.votek.pdfConverter.api.FileResponse;
import com.votek.pdfConverter.api.PdfConverterEngine;
import com.votek.pdfConverter.api.FileData.Format;
import com.votek.pdfConverter.impl.transformation.*;

/**
 * For testing purposes
 * 
 * @author HP
 *
 */
public class Main {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage :");
			System.out.println("java -jar program.jar  filePath  scale  format");
			System.out.println("-scale : double value in %. Ex : 100 to indicate 100%");
			System.out.println("-format : one of these strings A0, A1, A2, A3, A4, A5, A6");
			System.out.println("Thanks !");
			return;
		}

		int n = args.length;

		double scale = Double.valueOf(args[n - 2]);
		Format format = Format.valueOf(args[n - 1]);

		run(String.join(" ", Arrays.copyOfRange(args, 0, n - 2)), scale, format);
	}

	public static void run(String path, double scale, Format format) {
		try {

			PdfConverterEngine engine = PdfConverterEngine.engine();
			// Ex : engine.addFileConverter(converter);
			Configuration conf = Configuration.from(new File("./tmp"), new File("./out"));
			// Ex : conf.addFileProcessor(processor);
			scale = 100;
			format = Format.A4;
			FileData fileData = FileData.from(new File(path), scale, format,
					Arrays.asList(new PdfCopyTransformation(conf.getTempDir(), 10),
							// new PdfSkipPagesTransformation(1, 20),
							// new PdfTakePagesTransformation(10, 30),
							new PdfScaleTransformation(scale, format, 40), new PdfPageCountTransformation(50)));
			// FileData fileData = FileData.from(new File(path), scale, format);
			List<FileResponse> files = engine.convert(fileData, conf);

			System.out.println("End");
			files.stream().forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
