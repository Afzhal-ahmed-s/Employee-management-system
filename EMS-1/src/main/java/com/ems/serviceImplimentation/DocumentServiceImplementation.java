package com.ems.serviceImplimentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.enums.DocumentType;
import com.ems.exception.DocumentException;
import com.ems.model.Document;
import com.ems.repository.DocumentRepository;
import com.ems.service.DocumentService;

@Service
public class DocumentServiceImplementation implements DocumentService{

	
	@Autowired
	private DocumentRepository documentRepository;

	
	@Override
	public Document getDocumentByID(Integer documentId) throws DocumentException {

		Document document = documentRepository.findById(documentId).orElseThrow( ()->  new DocumentException("Incorrect document ID number.") );
		
		return document;
	}

	@Override
	public Document saveDocument(Document document) throws DocumentException {
		
		
		for(DocumentType e : DocumentType.values()) {
			if(document.getType().toString().toUpperCase().compareToIgnoreCase(e.toString()) == 0) {
				document.setType(document.getType().toString().toUpperCase());
				return documentRepository.save(document);
			}
		}
		throw new DocumentException("Please enter proper document type as per the options: AADHAR / PAN / PASSPORT / EPF / UAN / DL / SALARYSLIP / FORM16 ");				
		
	}

	@Override
	public Document deleteDocumentById(Integer documentId) throws DocumentException {

		Document document = documentRepository.findById(documentId).orElseThrow( ()-> new DocumentException("No document with such ID found.") );
		
		System.out.println(document.getFile());
		
		for(Document doc : getAllDocuments()) {
			if(doc.equals(document)) {
				documentRepository.delete(document);
				break;
			}
		}
		
		return document;
	}

	@Override
	public List<Document> getAllDocuments() {

		return documentRepository.findAll();
	}

	@Override
	public Document updateDocumentById(Document document, Integer documentId) throws DocumentException {
		
		Document foundDocument = documentRepository.findById(documentId).orElseThrow( ()-> new DocumentException("No such document with documentId found.") ); 
		
		if(document.getDateOfExpiry() != null)foundDocument.setDateOfExpiry(document.getDateOfExpiry());
		if(document.getDateOfIssue() != null)foundDocument.setDateOfIssue(document.getDateOfIssue());
		if(document.getFile() != null)foundDocument.setFile(document.getFile());
		if(document.getType() != null){
			
			for(DocumentType e : DocumentType.values()) {
				if(document.getType().toString().toUpperCase().compareToIgnoreCase(e.toString()) == 0) {
					foundDocument.setType(e.toString().toUpperCase());
				}
			}
			
		}
		if(document.getNumber() != null)foundDocument.setNumber(document.getNumber());

		return documentRepository.save(foundDocument);
		
	}



	
	
}
