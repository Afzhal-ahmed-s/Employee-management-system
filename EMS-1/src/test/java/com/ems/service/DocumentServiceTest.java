package com.ems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.ems.enums.DocumentType;
import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.repository.DocumentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.serviceImplimentation.DocumentServiceImplementation;

@ExtendWith(MockitoExtension.class)
@PrepareForTest(DocumentService.class)
public class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImplementation documentService;
    
    @Mock
    private EmployeeRepository employeeRepository;
    
    @Mock
    private File mockDir;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    //get document by id
    @Test
    public void testGetDocumentByID() throws DocumentException {
        // Arrange
        Integer documentId = 123;
        Document expectedDocument = new Document();
        expectedDocument.setDocumentId(documentId);
        expectedDocument.setType(DocumentType.PASSPORT.toString());
        expectedDocument.setNumber("123456789");

        when(documentRepository.findById(documentId)).thenReturn(Optional.of(expectedDocument));

        // Act
        Document actualDocument = documentService.getDocumentByID(documentId);

        // Assert
        assertNotNull(actualDocument);
        assertEquals(documentId, actualDocument.getDocumentId());
        assertEquals(DocumentType.PASSPORT.toString(), actualDocument.getType());
        assertEquals("123456789", actualDocument.getNumber());

        verify(documentRepository, times(1)).findById(documentId);
    }

    @Test
    public void testGetDocumentByID_ThrowsDocumentException() {
        // Arrange
        Integer documentId = 123;

        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(DocumentException.class, () -> documentService.getDocumentByID(documentId));

        verify(documentRepository, times(1)).findById(documentId);
    }
    
    //get all documents
    @Test
    public void testGetAllDocuments() {
        // Create some example documents
        Document document1 = new Document();
        document1.setDocumentId(1);
        document1.setNumber("123456");
        document1.setType("Passport");
        document1.setDateOfIssue(LocalDate.of(2020, 1, 1));
        document1.setDateOfExpiry(LocalDate.of(2030, 12, 31));
        document1.setFile(Arrays.asList("file1.jpg", "file2.jpg"));

        Document document2 = new Document();
        document2.setDocumentId(2);
        document2.setNumber("654321");
        document2.setType("PAN");
        document2.setDateOfIssue(LocalDate.of(2021, 3, 15));
        document2.setDateOfExpiry(LocalDate.of(2031, 2, 28));
        document2.setFile(Arrays.asList("file3.jpg", "file4.jpg"));

        List<Document> expectedDocuments = Arrays.asList(document1, document2);

        // Configure the mock repository to return the expected documents
        when(documentRepository.findAll()).thenReturn(expectedDocuments);

        // Call the service method
        List<Document> actualDocuments = documentService.getAllDocuments();

        // Assert that the returned documents match the expected documents
        assertEquals(expectedDocuments, actualDocuments);
    }
    
    //update document
    @Test
    public void testUpdateDocumentById() throws DocumentException {
        // Prepare test data
        Integer documentId = 1;

        Document existingDocument = new Document();
        existingDocument.setDocumentId(documentId);
        existingDocument.setNumber("123456");
        existingDocument.setType("Passport");
        existingDocument.setDateOfIssue(LocalDate.of(2020, 1, 1));
        existingDocument.setDateOfExpiry(LocalDate.of(2030, 12, 31));
        existingDocument.setFile(Arrays.asList("file1.jpg", "file2.jpg"));

        Document updatedDocument = new Document();
        updatedDocument.setDateOfExpiry(LocalDate.of(2035, 12, 31));
        updatedDocument.setFile(Arrays.asList("file3.jpg", "file4.jpg"));

        Document expectedDocument = new Document();
        expectedDocument.setDocumentId(documentId);
        expectedDocument.setNumber("123456");
        expectedDocument.setType("Passport");
        expectedDocument.setDateOfIssue(LocalDate.of(2020, 1, 1));
        expectedDocument.setDateOfExpiry(LocalDate.of(2035, 12, 31));
        expectedDocument.setFile(Arrays.asList("file3.jpg", "file4.jpg"));

        // Configure the mock repository to return the existing document when findById is called
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(existingDocument));

        // Configure the mock repository to return the expected updated document when save is called
        when(documentRepository.save(existingDocument)).thenReturn(expectedDocument);

        // Call the service method
        Document actualDocument = documentService.updateDocumentById(updatedDocument, documentId);

        // Assert that the returned document matches the expected document
        assertEquals(expectedDocument, actualDocument);
    }
    
   
    
    //saveDocument
    
    @Test
    public void testSaveDocument() throws DocumentException, IllegalStateException, IOException, EmployeeException {
        // Prepare test data
        String employeeID = "NODUCO001";

        Employee employee = new Employee();
        employee.setEmployeeID(employeeID);
        employee.setDocuments(new ArrayList<>());
        //employee.setComputationalEmployeeId(1);

        Document document = new Document();
        document.setNumber("123456");
        document.setType("Passport");
        document.setDateOfIssue(LocalDate.of(2022, 1, 1));
        document.setDateOfExpiry(LocalDate.of(2032, 12, 31));
        document.setFile(Arrays.asList("file1.jpg", "file2.jpg"));

        String expectedResponse = "PASSPORT 123456 saved for NODUCO001 .";

        // Configure the mock repository to return the employee when findByEmployeeID is called
        when(employeeRepository.findByEmployeeID(employeeID)).thenReturn(Optional.of(employee));

        // Call the service method
        String actualResponse = documentService.saveDocument(employeeID, document);

        // Assert that the returned response matches the expected response
        assertEquals(expectedResponse, actualResponse);

        // Assert that the employee's list of documents has been updated
        assertEquals(1, employee.getDocuments().size());
        assertEquals(document, employee.getDocuments().get(0));
    }

    @Test
    public void testSaveDocument_employeeNotFound() {
        // Prepare test data
        String employeeID = "NODUCO001";
        Document document = new Document();

        // Configure the mock repository to return an empty optional when findByEmployeeID is called
        when(employeeRepository.findByEmployeeID(employeeID)).thenReturn(Optional.empty());

        // Call the service method and assert that it throws an EmployeeException
        assertThrows(EmployeeException.class, () -> documentService.saveDocument(employeeID, document));
    }

    @Test
    public void testSaveDocument_existingDocumentOfTypeExists() {
        // Prepare test data
        String employeeID = "NODUCO001";

        Employee employee = new Employee();
        employee.setEmployeeID(employeeID);
        employee.setComputationalEmployeeId(1);
        employee.setDocuments(Arrays.asList(
                createDocument("AADHAR"),
                createDocument("PAN"),
                createDocument("PASSPORT")
        ));

        Document document = createDocument("PASSPORT");

        // Configure the mock repository to return the employee when findByEmployeeID is called
        when(employeeRepository.findByEmployeeID(employeeID)).thenReturn(Optional.of(employee));

        // Call the service method and assert that it throws a DocumentException
        assertThrows(DocumentException.class, () -> documentService.saveDocument(employeeID, document));
    }

    // Helper method to create a document with the given type
    private Document createDocument(String type) {
        Document document = new Document();
        document.setType(type);
        return document;
    }
    
    //upload multi part files

    @Test
    public void uploadMultipartFiles_shouldReturnFileNames() throws Exception {
        // Mock the input files
        List<MultipartFile> mockFiles = new ArrayList<>();
        mockFiles.add(new MockMultipartFile("file1.txt", "file1.txt", "text/plain", "File 1 Content".getBytes()));
        mockFiles.add(new MockMultipartFile("file2.txt", "file2.txt", "text/plain", "File 2 Content".getBytes()));

        // Mock the folder name
        String folderName = "testFolder";

        // Create a spy object for the DocumentService class
        //DocumentService documentServiceSpy = PowerMockito.spy(documentService);

        // Mock the behavior of the constructor
        //CustomFile mockFile = PowerMockito.mock(CustomFile.class);

        // Perform the method invocation
        List<String> result = documentService.uploadMultipartFiles(mockFiles, folderName);

        // Verify the expected file names
        List<String> expectedFileNames = new ArrayList<>();
        expectedFileNames.add("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + folderName + File.separator + "file1.txt");
        expectedFileNames.add("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + folderName + File.separator + "file2.txt");
        assertEquals(expectedFileNames, result);
        
        File f = new File("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");
    	deleteDirectory(new File( f.getParent()) );
        
    }

    // Custom File wrapper class
    private static class CustomFile extends File {
        public CustomFile(String path, File file) {
            super(path);
        }
    }   
    
    //delete directory
    @Test
    public void deleteDirectory_shouldDeleteDirectoryAndItsContents() throws IOException {
        // Create a temporary directory for testing
    	File f = new File("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/testFolder");
    	f.mkdirs();

        // Call the deleteDirectory method
    	File file = new File(f.getParent());
        documentService.deleteDirectory(file);

        // Verify that the directory and its contents have been deleted
        assertFalse(f.exists());
        assertFalse(f.isDirectory());
        assertFalse(hasFilesOrDirectories(f));
    }

    private File createTemporaryDirectory() throws IOException {
        File tempDir = File.createTempFile("tempDir", "");
        tempDir.delete();
        tempDir.mkdir();
        return tempDir;
    }

    private void createTemporaryFilesAndDirectories(File parentDir) throws IOException {
        File file1 = new File(parentDir, "file1.txt");
        File file2 = new File(parentDir, "file2.txt");
        File subDir1 = new File(parentDir, "subdir1");
        File subDir2 = new File(parentDir, "subdir2");

        file1.createNewFile();
        file2.createNewFile();
        subDir1.mkdir();
        subDir2.mkdir();
    }

    private boolean hasFilesOrDirectories(File directory) {
        File[] files = directory.listFiles();
        return files != null && files.length > 0;
    }
    
    //recursive deletion of files
    public void deleteDirectory(File file)
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

