package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.exception.DocumentException;
import com.ems.model.Document;

@Service
public interface DocumentService {

	public List<Document> getAllDocuments();
	public Document getDocumentByID(Integer documentId) throws DocumentException;
	public Document saveDocument(Document document) throws DocumentException;
	public Document deleteDocumentById(Integer documentId)throws DocumentException;
	public Document updateDocumentById(Document document, Integer documentId)throws DocumentException;
}
