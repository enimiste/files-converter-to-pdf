package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FileConfigurationException;

import java.io.File;
import java.util.LinkedHashSet;

public interface Configuration {
	File getTempDir();

	File getOutDir();

	/**
	 * @param tempDir
	 * @param outDir
	 * @param processors
	 * @return
	 */
	static Configuration of(File tempDir, File outDir, LinkedHashSet<FileProcessor> processors)
			throws FileConfigurationException {
		return new ConfigurationImpl(tempDir, outDir, processors == null ? new LinkedHashSet<>() : processors);
	}

	void addFileProcessor(FileProcessor processor);

	/**
	 * @param tempDir
	 * @param outDir
	 * @return
	 */
	static Configuration of(File tempDir, File outDir)
			throws FileConfigurationException {
		return of(tempDir, outDir, new LinkedHashSet<>());
	}

	LinkedHashSet<FileProcessor> getProcessor();
}
