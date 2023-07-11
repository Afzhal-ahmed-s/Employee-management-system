package com.ems.serviceImplimentation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatPrecisionException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ems.enums.DocumentType;
import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.repository.DocumentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.service.DocumentService;
import com.ems.service.EmployeeService;

@Service
public class DocumentServiceImplementation implements DocumentService{

	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	String statusCode_NOT_FOUND = "HttpStatus.NOT_FOUND*_";

	
	List<String> nonRecurringProof = Arrays.asList("AADHAR", "PAN", "PASSPORT");
	
	
	@Override
	public Document getDocumentByID(Integer documentId) throws DocumentException {

		Document document = documentRepository.findById(documentId).orElseThrow( ()->  new DocumentException(statusCode_NOT_FOUND + "Incorrect document ID number.") );
		
		return document;
	}

	@Override
	public String saveDocument(String employeeID, Document document) throws DocumentException, IllegalStateException, IOException, EmployeeException {
		
		Optional<Employee> employee = employeeRepository.findByEmployeeID(employeeID);
		
		if(employee.isEmpty())throw new EmployeeException(statusCode_NOT_FOUND +"Employee with such ID not found.");
		
		Integer computationalEmployeeID = employee.get().getComputationalEmployeeId();
		
		if(employee == null)throw new EmployeeException("No such employee found.");
		
		String documentType = document.getType().toString().toUpperCase();
		String ans= documentType +" "+document.getNumber()+ " saved for "+ employeeID+" .";

		List<Document> employeesListOfDocuments = employee.get().getDocuments();
		
		Integer length = employee.get().getDocuments().size();
		
		if(document.getDateOfIssue()!= null && document.getDateOfExpiry()!= null && document.getDateOfIssue().isBefore(document.getDateOfExpiry())) {
		
		if(length == 0) {
			document.setType(documentType);
			employeesListOfDocuments.add(document);
			employee.get().setDocuments( employeesListOfDocuments );
			Employee emp = employeeRepository.save(employee.get());
			return ans;
		}
		else {
			
			String AADHAR = "AADHAR";
			String PAN = "PAN";
			String PASSPORT = "PASSPORT";
			
			Boolean nonRecurringDocumentPresent = false;
			
			for(Document d : employeesListOfDocuments) {
				
				 if(documentType.equalsIgnoreCase(AADHAR) && d.getType().equalsIgnoreCase(AADHAR)) {
					 throw new DocumentException("AADHAR document already exists for employee "+ employeeID);		
				 }
				 else if(documentType.equalsIgnoreCase(PAN) && d.getType().equalsIgnoreCase(PAN)) {
					 throw new DocumentException("PAN document already exists for employee "+ employeeID);		
				 }
				 else if(documentType.equalsIgnoreCase(PASSPORT) && d.getType().equalsIgnoreCase(PASSPORT)) {
					 throw new DocumentException("PASSPORT document already exists for employee "+ employeeID);		
				 }
				 else continue;
				 
			}
			
			for(String s : nonRecurringProof) {
				if(s.equalsIgnoreCase(documentType))nonRecurringDocumentPresent = true;
			}
			
			if(nonRecurringDocumentPresent) {
				document.setType(documentType);
				employeesListOfDocuments.add(document);
				employee.get().setDocuments( employeesListOfDocuments );
				Employee emp = employeeRepository.save(employee.get());
				return ans;
				//return documentRepository.save(document);
			}
			
			else {
				
				String EPF = "EPF";
				String UAN = "UAN";
				String DL = "DL";
				String SALARYSLIP = "SALARYSLIP";
				String FORM16 = "FORM16";
				
				if(documentType.equalsIgnoreCase(EPF)) {
					
					List<Document> listOfRespectiveCategoryDocuments = new ArrayList<>();
					
					for(Document doc : employeesListOfDocuments) {
						if(doc.getType().equalsIgnoreCase(EPF))listOfRespectiveCategoryDocuments.add(doc);
					}
					
					
					for(Document epfDoc : listOfRespectiveCategoryDocuments) {
						if(epfDoc.getDateOfIssue().isEqual(document.getDateOfIssue()) && epfDoc.getDateOfExpiry().isEqual(document.getDateOfExpiry())) {
							throw new DocumentException("This EPF document already exists for "+ employeeID);
						}
					}
				
					document.setType(documentType);
					employeesListOfDocuments.add(document);
					employee.get().setDocuments( employeesListOfDocuments );
					employeeRepository.save(employee.get());
					return ans;
					
				}
				
				else if(documentType.equalsIgnoreCase(UAN)) {
					
					List<Document> listOfRespectiveCategoryDocuments = new ArrayList<>();
					
					for(Document doc : employeesListOfDocuments) {
						if(doc.getType().equalsIgnoreCase(UAN))listOfRespectiveCategoryDocuments.add(doc);
					}
					
					
					for(Document uanDoc : listOfRespectiveCategoryDocuments) {
						if(uanDoc.getDateOfIssue().isEqual(document.getDateOfIssue()) && uanDoc.getDateOfExpiry().isEqual(document.getDateOfExpiry())) {
							throw new DocumentException("This UAN document already exists for "+ employeeID);
						}
					}
			
					document.setType(documentType);
					employeesListOfDocuments.add(document);
					employee.get().setDocuments( employeesListOfDocuments );
					employeeRepository.save(employee.get());
					return ans;
				}
				
				else if(documentType.equalsIgnoreCase(DL)) {
					
					List<Document> listOfRespectiveCategoryDocuments = new ArrayList<>();
					
					for(Document doc : employeesListOfDocuments) {
						if(doc.getType().equalsIgnoreCase(DL))listOfRespectiveCategoryDocuments.add(doc);
					}
					
					
					for(Document dlDoc : listOfRespectiveCategoryDocuments) {
						if(dlDoc.getDateOfIssue().isEqual(document.getDateOfIssue()) && dlDoc.getDateOfExpiry().isEqual(document.getDateOfExpiry())) {
							throw new DocumentException("This DL document already exists for "+ employeeID);
						}
					}
				
					document.setType(documentType);
					employeesListOfDocuments.add(document);
					employee.get().setDocuments( employeesListOfDocuments );
					employeeRepository.save(employee.get());
					return ans;
				}
				
				else if(documentType.equalsIgnoreCase(SALARYSLIP)) {
					
					List<Document> listOfRespectiveCategoryDocuments = new ArrayList<>();
					
					for(Document doc : employeesListOfDocuments) {
						if(doc.getType().equalsIgnoreCase(SALARYSLIP))listOfRespectiveCategoryDocuments.add(doc);
					}
					
					
					for(Document salarySlipDoc : listOfRespectiveCategoryDocuments) {
						if(salarySlipDoc.getDateOfIssue().isEqual(document.getDateOfIssue()) && salarySlipDoc.getDateOfExpiry().isEqual(document.getDateOfExpiry())) {
							throw new DocumentException("This SALARYSLIP document already exists for "+ employeeID);
						}
					}
					
					document.setType(documentType);
					employeesListOfDocuments.add(document);
					employee.get().setDocuments( employeesListOfDocuments );
					employeeRepository.save(employee.get());
					return ans;
				}
				
				else if(documentType.equalsIgnoreCase(FORM16)) {
					
					List<Document> listOfRespectiveCategoryDocuments = new ArrayList<>();
					
					for(Document doc : employeesListOfDocuments) {
						if(doc.getType().equalsIgnoreCase(FORM16))listOfRespectiveCategoryDocuments.add(doc);
					}
					
					
					for(Document form16Doc : listOfRespectiveCategoryDocuments) {
						if(form16Doc.getDateOfIssue().isEqual(document.getDateOfIssue()) && form16Doc.getDateOfExpiry().isEqual(document.getDateOfExpiry())) {
							throw new DocumentException("This FORM16 document already exists for "+ employeeID);
						}
					}
					

					document.setType(documentType);
					employeesListOfDocuments.add(document);
					employee.get().setDocuments( employeesListOfDocuments );
					employeeRepository.save(employee.get());
					return ans;
				}
				
				else throw new DocumentException("Error occured not matching any of the conditions.");
				
			}
			
		}
		
		}
		throw new DocumentException("The date of issue and the date of expiry are not valid.");
			
			
	}

	@Override
	public Document deleteDocumentById(Integer documentId) throws DocumentException {

		Document document = documentRepository.findById(documentId).orElseThrow( ()-> new DocumentException(statusCode_NOT_FOUND + "No document with such ID found.") );
		
		System.out.println(document.getFile());
		
		if(document.getFile()!= null &&  document.getFile().size() > 0) {
			String pathToDelete = document.getFile().get(0);
			
			File filePath = Paths.get(pathToDelete).getParent().toFile();
			
			deleteDirectory(filePath);

		}
				
		documentRepository.delete(document);
		
		return document;
	}

	@Override
	public List<Document> getAllDocuments() {

		return documentRepository.findAll();
	}

	@Override
	public Document updateDocumentById(Document document, Integer documentId) throws DocumentException {
		
		Document foundDocument = documentRepository.findById(documentId).orElseThrow( ()-> new DocumentException(statusCode_NOT_FOUND + "No such document with documentId found.") ); 
		
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

	@Override
	public List<String> uploadMultipartFiles(List<MultipartFile> files, String foldername) throws IllegalStateException, IOException {
		
		List<String> fileNames = new ArrayList<>();
		
		File theDir = new File("/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + foldername);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		for(MultipartFile e : files) {
	         String filePath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + foldername + System.getProperty("file.separator") + e.getOriginalFilename();
	         e.transferTo(new File(filePath));
	         fileNames.add(filePath);
	 	}

       return fileNames;
	}
	
	@Override
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
