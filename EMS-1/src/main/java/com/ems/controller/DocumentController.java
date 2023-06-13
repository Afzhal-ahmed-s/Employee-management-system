package com.ems.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.service.DocumentService;


@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	public DocumentService documentService;
	
	
	 @PostMapping("/upload")
	    public List<String> uploadFiles(@RequestParam("files") List<MultipartFile> files, String foldername) throws IllegalStateException, IOException {

	        return documentService.uploadMultipartFiles(files, foldername);
	    }
	 
//	 @GetMapping("/download")
//	 public ResponseEntity<ByteArrayResource> downloadFiles(@RequestParam String fileName) throws IOException {
//		 
//		 String filePath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/"+ fileName;	        
//
//		 File file = new File(filePath);
//	      HttpHeaders headers = new HttpHeaders();
//	      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//	      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//	      headers.add("Pragma", "no-cache");
//	      headers.add("Expires", "0");
//	      
//	      Path path = Paths.get(filePath);
//	      
//
//	      ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//	      
//	      
//	      return ResponseEntity.ok().headers(headers)
//	         .contentLength(file.length())
//	         .contentType(MediaType.APPLICATION_OCTET_STREAM)
//	         .body(resource);
//
//	 }
	
	
	@GetMapping("/{documentId}")
	public ResponseEntity<Document> getDocumentById(@PathVariable Integer documentId) throws DocumentException{
		
		 Document document = documentService.getDocumentByID(documentId);
		
		 return new ResponseEntity<Document>(document,HttpStatus.OK);
	}
	
	@PostMapping("/{employeeID}")
	public ResponseEntity<String> addDoccument(@PathVariable String employeeID, @RequestBody Document document) throws DocumentException, IllegalStateException, IOException, EmployeeException{
		
		String persistedDocument = documentService.saveDocument(employeeID, document);
		
		return new ResponseEntity<String>(persistedDocument,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{documentId}")
	public ResponseEntity<Document> deleteDocumentById(@PathVariable Integer documentId) throws DocumentException{
		
		Document document = documentService.deleteDocumentById(documentId);
		return new ResponseEntity<Document>(document,HttpStatus.ACCEPTED);
		
	}
	
	@PatchMapping("/{documentId}")
	public ResponseEntity<Document> updateDocumentById(@RequestBody Document document, @PathVariable Integer documentId)throws DocumentException{
		
		Document ans = documentService.updateDocumentById(document, documentId);
		return new ResponseEntity<Document>(ans,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllDocuments")
	public ResponseEntity<List<Document>> getAllDocuments() throws DocumentException{
		
		 List<Document> listOfDocuments = documentService.getAllDocuments();
		
		 return new ResponseEntity<List<Document>>(listOfDocuments,HttpStatus.OK);
	}
	
}
