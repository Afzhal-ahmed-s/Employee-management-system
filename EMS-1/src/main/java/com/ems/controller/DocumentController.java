package com.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.exception.DocumentException;
import com.ems.model.Document;
import com.ems.service.DocumentService;

@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	public DocumentService DocumentService;
	
	@GetMapping("/{documentId}")
	public ResponseEntity<Document> getDocumentById(@PathVariable Integer documentId) throws DocumentException{
		
		 Document document = DocumentService.getDocumentByID(documentId);
		
		 return new ResponseEntity<Document>(document,HttpStatus.ACCEPTED);
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
	
	@PutMapping("/{documentId}")
	public ResponseEntity<Document> updateDocumentById(@RequestBody Document document, @PathVariable Integer documentId)throws DocumentException{
		
		Document ans = DocumentService.updateDocumentById(document, documentId);
		return new ResponseEntity<Document>(ans,HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllDocuments")
	public ResponseEntity<List<Document>> getAllDocuments() throws DocumentException{
		
		 List<Document> listOfDocuments = DocumentService.getAllDocuments();
		
		 return new ResponseEntity<List<Document>>(listOfDocuments,HttpStatus.ACCEPTED);
	}
	
}
