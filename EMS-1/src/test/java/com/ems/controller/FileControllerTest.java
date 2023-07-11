package com.ems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import com.ems.exception.FileException;
import com.ems.serviceImplimentation.FileService;

@PrepareForTest(Environment.class)
public class FileControllerTest {

    @Mock
    private FileService fileService;
    
    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDownloadFiles() throws IOException, FileException {
        // Prepare the test data
        String employeeID = "EMP001";
        String documentType = "PAN";
        String folderPath = "/path/to/files";
        String zipFileName = documentType;

        byte[] zipBytes = {0, 1, 2, 3, 4};

     // Create a mock instance of Environment
        Environment mockEnvironment = mock(Environment.class);
        String filePath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/";
        // Define the behavior of the mock
        when(mockEnvironment.getProperty("filePath")).thenReturn(filePath);

        when(fileService.zipDirectory(filePath + employeeID +"/"+ documentType)).thenReturn(zipBytes);
        // Set the mock environment in the class under test
        fileController.environment = mockEnvironment;
        // Invoke the downloadFiles method of the FileController
        ResponseEntity<byte[]> response = fileController.downloadFiles(employeeID, documentType);

        // Verify the interactions and assertions
        assertEquals(zipBytes, response.getBody());
        assertEquals("attachment; filename=" + zipFileName + ".zip", response.getHeaders().getFirst("Content-Disposition"));
    }
}


