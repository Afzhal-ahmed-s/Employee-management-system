package com.ems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ems.exception.FileException;
import com.ems.serviceImplimentation.FileService;

public class FileServiceTest {

	@InjectMocks
    private FileService fileService;

    @Mock
    private File mockFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testZipDirectory() throws IOException, FileException {
        // Arrange
        Path path = Paths.get("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");
    	File f = new File("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");

        if(Files.exists(path)) {
        	deleteDirectory(new File( f.getParent()) );
        }
            	
    	f.mkdirs();
    	    	
        List<File> fileList = new ArrayList<>();
        fileList.add(f);


        // Act
        byte[] result = fileService.zipFiles(fileList);

        // Assert
        assertNotNull(result);
        
    	deleteDirectory(new File( f.getParent()) );

    }


    @Test
    public void testZipFiles_WithDirectory() throws IOException, FileException {
        // Arrange
        List<File> fileList = new ArrayList<>();
        fileList.add(mockFile);
        when(mockFile.isDirectory()).thenReturn(true);
        when(mockFile.toString()).thenReturn("directory");

        // Act
        byte[] result = fileService.zipFiles(fileList);

        // Assert
        assertNotNull(result);
        // Add additional assertions for the expected result
    }

    @Test
    public void testGetFileList_WithFile() throws FileException {
        // Arrange
        when(mockFile.isFile()).thenReturn(true);

        // Act
        List<File> fileList = fileService.getFileList(mockFile);

        // Assert
        assertEquals(1, fileList.size());
        // Add additional assertions for the expected result
    }

    @Test
    public void testGetFileList_WithDirectory() throws FileException, IOException {
        // Arrange
    	Path path = Paths.get("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");
     	File f = new File("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");

         if(Files.exists(path)) {
         	deleteDirectory(new File( f.getParent()) );
         }
             	
     	f.mkdirs();


        // Act
        List<File> fileList = fileService.getFileList(f);

        // Assert
        assertEquals(1, fileList.size());
        

        // Delete the file
    	deleteDirectory(new File( f.getParent()) );
    }
    


    @Test
    public void testGetFileName() {
        // Arrange
        String filePath = "/path/to/file.txt";

        // Act
        String fileName = fileService.getFileName(filePath);

        // Assert
        assertEquals("file.txt", fileName);
    }
    
    private void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {
 
            // if it is a subfolder,e.g Rohan and Ritik,
            //  recursively call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
 
            // delete files and empty subfolders
            subfile.delete();
        }
    }
}

