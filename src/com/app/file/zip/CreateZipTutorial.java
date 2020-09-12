package com.app.file.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;


public class CreateZipTutorial {

	public static void main(String[] args) throws IOException, ArchiveException {
		processFiles();

	}
	
	public static void processFiles() throws IOException, ArchiveException {
		String source="E:\\FileManagement\\inputFiles";
		String destination="E:\\FileManagement\\outputFiles\\testzipattachment.zip";
		File outputZipFile = new File(destination);
		try( ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputZipFile)){;
			createZip(zipArchiveOutputStream,  source);
		}
		System.out.println(">>>>>>>>>Zip File Created Successfully>>>>>>>>>");
	}

	/**
	 * create zip file
	 * @param zipArchiveOutputStream
	 * @param fileDir
	 * @throws IOException
	 * @throws ArchiveException
	 */
	public static void createZip(ZipArchiveOutputStream zipArchiveOutputStream, String fileDir) throws IOException, ArchiveException {
		try (Stream<Path> walk = Files.walk(Paths.get(fileDir))) {

			List<File> fileList = walk.filter(Files::isRegularFile).map(x -> x.toFile()).collect(Collectors.toList());

			for(File file : fileList) {
				
				int index = file.getAbsoluteFile().getParent().lastIndexOf(File.separator);
				String parentDirectory = file.getAbsoluteFile().getParent().substring(index+1); ZipArchiveEntry
				zipArchiveEntry = new ZipArchiveEntry(parentDirectory+File.separator+file.getName());
				zipArchiveEntry.setMethod(ZipEntry.DEFLATED);
				zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry); IOUtils.copy(new FileInputStream(file), zipArchiveOutputStream);
				zipArchiveOutputStream.closeArchiveEntry();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
