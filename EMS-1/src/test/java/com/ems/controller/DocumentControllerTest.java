package com.ems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.service.DocumentService;
import com.ems.serviceImplimentation.DocumentServiceImplementation;

public class DocumentControllerTest {

    @Mock
    private DocumentServiceImplementation documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadFiles() throws IOException {
        // Prepare the test files
        MockMultipartFile file1 = new MockMultipartFile("file", "file1.txt", "text/plain", "File 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "file2.txt", "text/plain", "File 2".getBytes());

        // Prepare the expected response
        List<String> expectedFiles = List.of("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/"+"file1.txt", "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/"+"file2.txt");
        ResponseEntity<List<String>> expectedResponse = new ResponseEntity<>(expectedFiles, HttpStatus.OK);

        // Mock the behavior of the DocumentService's uploadMultipartFiles method
        when(documentService.uploadMultipartFiles(anyList(), anyString())).thenReturn(expectedFiles);

        // Invoke the uploadFiles method of the DocumentController
        List<String> actualResponse = documentController.uploadFiles(List.of(file1, file2), "testFolder");

        // Verify the interactions and assertions
        assertEquals(expectedFiles, actualResponse);
        verify(documentService, times(1)).uploadMultipartFiles(anyList(), anyString());
    }


    @Test
    public void testGetDocumentById() throws DocumentException {
        // Prepare test data
        Integer documentId = 1;
        Document expectedDocument = new Document(documentId, "123456", "Passport", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file1.txt", "file2.txt"));

        // Configure mock behavior
        when(documentService.getDocumentByID(eq(documentId))).thenReturn(expectedDocument);

        // Perform the API call
        ResponseEntity<Document> response = documentController.getDocumentById(documentId);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDocument, response.getBody());

        // Verify that the service method was called with the correct argument
        verify(documentService).getDocumentByID(eq(documentId));
    }

    @Test
    public void testAddDocument() throws DocumentException, IllegalStateException, IOException, EmployeeException {
        // Prepare test data
        String employeeID = "123";
        Document document = new Document(null, "123456", "Passport", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file1.txt", "file2.txt"));
        String persistedDocument = "persistedDocument";

        // Configure mock behavior
        when(documentService.saveDocument(eq(employeeID), eq(document))).thenReturn(persistedDocument);

        // Perform the API call
        ResponseEntity<String> response = documentController.addDoccument(employeeID, document);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(persistedDocument, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(documentService).saveDocument(eq(employeeID), eq(document));
    }

    @Test
    public void testDeleteDocumentById() throws DocumentException {
        // Prepare test data
        Integer documentId = 1;
        Document expectedDocument = new Document(documentId, "123456", "Passport", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file1.txt", "file2.txt"));

        // Configure mock behavior
        when(documentService.deleteDocumentById(eq(documentId))).thenReturn(expectedDocument);

        // Perform the API call
        ResponseEntity<Document> response = documentController.deleteDocumentById(documentId);

        // Verify the response
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(expectedDocument, response.getBody());

        // Verify that the service method was called with the correct argument
        verify(documentService).deleteDocumentById(eq(documentId));
    }

    @Test
    public void testUpdateDocumentById() throws DocumentException {
        // Prepare test data
        Integer documentId = 1;
        Document document = new Document(documentId, "123456", "Passport", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file1.txt", "file2.txt"));
        Document updatedDocument = new Document(documentId, "789", "Driver's License", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file3.txt", "file4.txt"));

        // Configure mock behavior
        when(documentService.updateDocumentById(eq(document), eq(documentId))).thenReturn(updatedDocument);

        // Perform the API call
        ResponseEntity<Document> response = documentController.updateDocumentById(document, documentId);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(updatedDocument, response.getBody());

        // Verify that the service method was called with the correct arguments
        verify(documentService).updateDocumentById(eq(document), eq(documentId));
    }

    @Test
    public void testGetAllDocuments() throws DocumentException {
        // Prepare test data
        List<Document> listOfDocuments = Arrays.asList(
                new Document(1, "123456", "Passport", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file1.txt", "file2.txt")),
                new Document(2, "789012", "Driver's License", LocalDate.now(), LocalDate.now().plusYears(5), Arrays.asList("file3.txt", "file4.txt"))
        );

        // Configure mock behavior
        when(documentService.getAllDocuments()).thenReturn(listOfDocuments);

        // Perform the API call
        ResponseEntity<List<Document>> response = documentController.getAllDocuments();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listOfDocuments, response.getBody());

        // Verify that the service method was called
        verify(documentService).getAllDocuments();
    }
}

