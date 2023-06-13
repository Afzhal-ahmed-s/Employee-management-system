package com.ems.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;

@Service
public interface DocumentService {

	public List<Document> getAllDocuments();
	public Document getDocumentByID(Integer documentId) throws DocumentException;
	public String saveDocument(String employeeID, Document document) throws DocumentException, IllegalStateException, IOException, EmployeeException;
	public Document deleteDocumentById(Integer documentId)throws DocumentException;
	public Document updateDocumentById(Document document, Integer documentId)throws DocumentException;
	
	public List<String> uploadMultipartFiles(List<MultipartFile> files, String foldername) throws IllegalStateException, IOException;
}
