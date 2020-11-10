package com.votek.pdfConverter.api.util;

import java.io.File;

import com.votek.pdfConverter.api.FileData;

public class FileHelper {
	/**
	 * Checks if the file/folder given as inputFile in the FileData object has a
	 * given extension or not
	 * 
	 * @param file
	 * @param exts
	 * @return
	 */
	public static boolean hasExtension(File file, String... exts) {
		for (String ext : exts) {
			if (file.toPath().toString().toLowerCase().endsWith("." + ext))
				return true;
		}
		return false;
	}

	/**
	 * Checks if the file/folder given as inputFile in the FileData object exists or
	 * not
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isPathExists(File file) {
		return file.exists();
	}

	/**
	 * Checks if the file/folder given as inputFile in the FileData object has a
	 * given extension or not
	 * 
	 * @param file
	 * @param exts
	 * @return
	 */
	public static boolean hasExtension(FileData file, String... exts) {
		for (String ext : exts) {
			if (file.getInputFile().toPath().toString().toLowerCase().endsWith("." + ext))
				return true;
		}
		return false;
	}

	/**
	 * Checks if the file/folder given as inputFile in the FileData object exists or
	 * not
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isPathExists(FileData file) {
		return file.getInputFile().exists();
	}
}
