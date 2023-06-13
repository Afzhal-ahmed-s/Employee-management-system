package com.ems.serviceImplimentation;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.ems.exception.FileException;

@Service
public class FileService {

	 private static final int BUFFER = 1024;
	 
	 String statusCode_NOT_FOUND = "HttpStatus.NOT_FOUND*_";

	    public byte[] zipDirectory(String sourceFolder) throws IOException, FileException {
	        List<File> fileList = getFileList(new File(sourceFolder));
	        return zipFiles(fileList);
	    }

	    private byte[] zipFiles(List<File> fileList) throws FileException {
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
	            for (File file : fileList) {
	                if (file.isDirectory()) {
	                    ZipEntry ze = new ZipEntry(getFileName(file.toString()) + "/");
	                    zos.putNextEntry(ze);
	                    zos.closeEntry();
	                } else {
	                    FileInputStream fis = new FileInputStream(file);
	                    BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

	                    ZipEntry ze = new ZipEntry(getFileName(file.toString()));
	                    zos.putNextEntry(ze);

	                    byte[] data = new byte[BUFFER];
	                    int count;
	                    while ((count = bis.read(data, 0, BUFFER)) != -1) {
	                        zos.write(data, 0, count);
	                    }

	                    bis.close();
	                    zos.closeEntry();
	                }
	            }
	        } catch (IOException ioExp) {
	            System.out.println("Error while zipping: " + ioExp.getMessage());
	            ioExp.printStackTrace();
	        }
	        
	        if(baos != null && baos.size() != 0)return baos.toByteArray();
	        else throw new FileException(statusCode_NOT_FOUND + "Empty file.");
	    }
	 
	
	    

	    private List<File> getFileList(File source) throws FileException {
	        List<File> fileList = new ArrayList<>();

	        if (source.isFile()) {
	            fileList.add(source);
	        } else if (source.isDirectory()) {
	            String[] subList = source.list();

	            if (subList.length == 0) {
	                fileList.add(new File(source.getAbsolutePath()));
	            }

	            for (String child : subList) {
	                fileList.addAll(getFileList(new File(source, child)));
	            }
	        }
	        else throw new FileException(statusCode_NOT_FOUND + "Empty file.");


	        return fileList;
	    }

	    private String getFileName(String filePath) {
	        String name = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	        return name;
	    }
	    

	
}
