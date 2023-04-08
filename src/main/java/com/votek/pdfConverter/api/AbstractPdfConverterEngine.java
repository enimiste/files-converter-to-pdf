package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HP
 */
public abstract class AbstractPdfConverterEngine implements PdfConverterEngine {
    /**
     * Checks the converters one after one to find the first one that can handle the
     * file
     *
     * @param file
     * @param conf
     * @return
     * @throws FileNotFoundException
     * @throws FileProcessingException
     * @throws FileConverterException
     */
    public List<FileResponse> convert(FileData file, Configuration conf)
            throws FileNotFoundException, NoConverterFoundException, FileProcessingException, FileConverterException {
        //Processors
        for (FileProcessor processor : conf.getProcessor()) {
            processor.process(file);
        }

        //Converters
        List<FileResponse> convertedFiles = new ArrayList<>();
        List<FileResponse> items;
        boolean handled = false;
        for (FileToPdfConverter converter : getConverters()) {
            if (converter.canHandle(file)) {
                items = converter.handle(file, conf);
                if (items != null)
                    convertedFiles.addAll(items);
                handled = true;
                break;
            }
        }
        if (!handled)
            throw new NoConverterFoundException(file.getInputFile());

        //Transformers
        List<FileResponse> transformedFiles = new ArrayList<>();
        for (FileResponse pdf : convertedFiles) {
            FileResponse res = getTransformer().applyTransformations(file.getTransformations(), pdf, conf);
            try {
                File outFile = new File(conf.getOutDir(), res.getOutputFile().getName());
                if (outFile.exists())
                    outFile.delete();
                Files.copy(res.getOutputFile().toPath(), outFile.toPath());
                // Delete the old file
                if (res.getOutputFile().exists())
                    res.getOutputFile().delete();
                transformedFiles.add(FileResponse.from(outFile, res.getPagesNumber()));
            } catch (IOException | InvalidFileResponseException e) {
                throw new FileConverterException(res.getOutputFile(), e);
            }
        }

        return transformedFiles;
    }
}
