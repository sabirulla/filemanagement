package com.app.file.zip;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.IOUtils;

public class CreateMultiPartZIpFileTutorial {

	public static void main(String[] args) throws IOException, ArchiveException {
		processZipFile2Split();

	}

	public static void processZipFile2Split() throws IOException, ArchiveException {
		String source="E:\\FileManagement\\multipartzipfile\\inputFiles\\hugearchive.zip";
		String destination="E:\\FileManagement\\multipartzipfile\\outputFiles\\splitzip.zip";
		
		File outputZipFile = new File(destination);
		//long splitSize = 100 * 1024L; /* 100 KB */
		long splitSize = 5 * 1024 * 1024; /* 5 MB */
		
		//try with resource
		
		try(
				ZipFile zipFilePath = new ZipFile(source);
				ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputZipFile, splitSize);
			) 
		{
			splitZip(zipArchiveOutputStream, zipFilePath);
		}
		System.out.println(">>>>>>>>>Multi Part zip file created Successfully from zip file>>>>>>>>>");
	}

	/**
	 * Split Zip file and create multi part compressed zip files eg., .zip,z01,.z02...
	 * @param zipArchiveOutputStream
	 * @param fileToAdd
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public static void splitZip(ZipArchiveOutputStream zipArchiveOutputStream, ZipFile fileToAdd) throws IOException, ArchiveException {
		Enumeration<ZipArchiveEntry> enumeration = fileToAdd.getEntries();
		while(enumeration.hasMoreElements()) {
			ZipArchiveEntry zipArchiveEntry =(ZipArchiveEntry) enumeration.nextElement();
			zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
			IOUtils.copy(fileToAdd.getInputStream(zipArchiveEntry), zipArchiveOutputStream);
			zipArchiveOutputStream.closeArchiveEntry();
		}
	}
}
