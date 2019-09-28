package com.votek.pdfConverter.api;

import java.io.File;
import java.util.PriorityQueue;

import com.votek.pdfConverter.api.exception.FileConfigurationException;

class ConfigurationImpl implements Configuration {
	protected File tempDir;
	protected File outDir;
	protected PriorityQueue<FileProcessor> processors;

	public ConfigurationImpl(File tempDir, File outDir, PriorityQueue<FileProcessor> processors)
			throws FileConfigurationException {
		super();
		this.tempDir = tempDir;
		this.outDir = outDir;
		this.processors = processors;
		if (!tempDir.exists())
			throw new FileConfigurationException("tempDir does'nt exists");
		if (!outDir.exists())
			throw new FileConfigurationException("outDir does'nt exists");
	}

	@Override
	public File getTempDir() {
		return tempDir;
	}

	@Override
	public File getOutDir() {
		return outDir;
	}

	@Override
	public PriorityQueue<FileProcessor> getProcessor() {
		return processors;
	}

	@Override
	public void addFileProcessor(FileProcessor processor) {
		processors.add(processor);
	}

}
