package com.ems.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.ems.model.Document;
import com.ems.service.DocumentService;

@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	public DocumentService DocumentService;
	
	
	 @PostMapping("/upload")
	    public String uploadFile(@RequestParam("files") List<MultipartFile> files) throws IllegalStateException, IOException {
	        // Process the uploaded file
	        // You can access the file details using the MultipartFile object

	        // Example: Save the file to a specific location
		 	System.out.println("Check version 2");
		 	
		 	for(MultipartFile e : files) {
		         String filePath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + e.getOriginalFilename();
		         e.transferTo(new File(filePath));

		 	}

	        return "Files uploaded successfully";
	    }
	
	
	@GetMapping("/{documentId}")
	public ResponseEntity<Document> getDocumentById(@PathVariable Integer documentId) throws DocumentException{
		
		 Document document = DocumentService.getDocumentByID(documentId);
		
		 return new ResponseEntity<Document>(document,HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<Document> addDoccument(@RequestBody Document document) throws DocumentException{
		
		Document persistedDocument = DocumentService.saveDocument(document);
		
		return new ResponseEntity<Document>(persistedDocument,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{documentId}")
	public ResponseEntity<Document> deleteDocumentById(@PathVariable Integer documentId) throws DocumentException{
		
		Document document = DocumentService.deleteDocumentById(documentId);
		return new ResponseEntity<Document>(document,HttpStatus.ACCEPTED);
		
	}
	
	@PatchMapping("/{documentId}")
	public ResponseEntity<Document> updateDocumentById(@RequestBody Document document, @PathVariable Integer documentId)throws DocumentException{
		
		Document ans = DocumentService.updateDocumentById(document, documentId);
		return new ResponseEntity<Document>(ans,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllDocuments")
	public ResponseEntity<List<Document>> getAllDocuments() throws DocumentException{
		
		 List<Document> listOfDocuments = DocumentService.getAllDocuments();
		
		 return new ResponseEntity<List<Document>>(listOfDocuments,HttpStatus.OK);
	}
	
}
