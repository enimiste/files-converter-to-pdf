package com.votek.pdfConverter.api;

import com.votek.pdfConverter.api.exception.FileConfigurationException;

import java.io.File;
import java.util.LinkedHashSet;

class ConfigurationImpl implements Configuration {
	protected File tempDir;
	protected File outDir;
	protected LinkedHashSet<FileProcessor> processors;

	public ConfigurationImpl(File tempDir, File outDir, LinkedHashSet<FileProcessor> processors)
			throws FileConfigurationException {
		super();
		this.tempDir = tempDir;
		this.outDir = outDir;
		this.processors = processors;
		if (!tempDir.exists())
			throw new FileConfigurationException("tempDir doesn't exists");
		if (!outDir.exists())
			throw new FileConfigurationException("outDir doesn't exists");
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
	public LinkedHashSet<FileProcessor> getProcessor() {
		return processors;
	}

	@Override
	public void addFileProcessor(FileProcessor processor) {
		processors.add(processor);
	}

}
