package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FilePdfTransformationException;

import java.util.LinkedHashSet;

public interface PdfTransformation {
    PdfTransformation NO_OP = (file, conf) -> null;

    FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException;

    class Chain implements PdfTransformation {
        private final LinkedHashSet<PdfTransformation> transformations;

        public Chain(LinkedHashSet<PdfTransformation> transformations) {
            this.transformations = transformations;
        }

        @Override
        public FileResponse apply(FileResponse file, Configuration conf) throws FilePdfTransformationException {
            if (transformations == null) return null;
            for (PdfTransformation transformation : transformations) file = transformation.apply(file, conf);
            return file;
        }
    }
}
