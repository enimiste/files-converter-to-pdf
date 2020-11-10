package com.votek.pdfConverter.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.votek.pdfConverter.api.exception.FileConfigurationException;

public interface Configuration {
	File getTempDir();

	File getOutDir();

	PriorityQueue<FileProcessor> getProcessor();

	void addFileProcessor(FileProcessor processor);

	/**
	 * 
	 * @param tempDir
	 * @param outDir
	 * @param processors
	 * @return
	 */
	static Configuration from(File tempDir, File outDir, List<FileProcessor> processors)
			throws FileConfigurationException {
		return new ConfigurationImpl(tempDir, outDir, new PriorityQueue<>(processors));
	}
	
	/**
	 * 
	 * @param tempDir
	 * @param outDir
	 * @return
	 */
	static Configuration from(File tempDir, File outDir)
			throws FileConfigurationException {
		return from(tempDir, outDir, new ArrayList<>());
	}
}
