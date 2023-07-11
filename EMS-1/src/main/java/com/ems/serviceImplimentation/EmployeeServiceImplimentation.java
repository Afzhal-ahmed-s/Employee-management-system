package com.ems.serviceImplimentation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.enums.Department;
import com.ems.enums.DocumentType;
import com.ems.enums.Gender;
import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.DateRange;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.model.NextNumberGenerator;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.LeaveRepository;
import com.ems.service.EmployeeService;

@Service
public class EmployeeServiceImplimentation implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private NextNumberGenerator nextNumberGenerator;
	
	String statusCode_NOT_FOUND = "HttpStatus.NOT_FOUND*_";
	
	StringBuilder NOD = new StringBuilder("NODUCO");
	
	@Override
	public Employee addEmployee(Employee employee) throws EmployeeException {
		
		//System.out.println("Check version 2: "+ employee.getEmployeeID()+", "+employee);
		
		Boolean genderFlag = false;
		for(Gender e : Gender.values()) {
			if(e.toString().compareToIgnoreCase(employee.getGender().toString()) == 0) {
				employee.setGender(e.toString());
				genderFlag = true;
				break;
			}
		}
		if(!genderFlag)throw new EmployeeException("Improper gender options provided.");

		
		Boolean departmentFlag = false;
		for(Department e : Department.values()) {
			if(e.toString().compareToIgnoreCase(employee.getDepartment().toString()) == 0) {
				employee.setDepartment(e.toString());
				departmentFlag = true;
				break;
			}
		}
		if(!departmentFlag)throw new EmployeeException("Improper department options provided.");

//		Version 2 change
		employee.setDocuments(new ArrayList<>());
		
		Integer number = nextNumberGenerator.getNextNumber();
		String employeeIDPackage = NOD.toString() + number;
		employee.setEmployeeID( employeeIDPackage );
		
		System.out.println("Check version2: "+ employeeIDPackage);
		
		return employeeRepository.save(employee);
		
	}
	

	@Override
	public Employee getEmployeeById(Integer employeeId) throws EmployeeException {
		
		Optional<Employee> emp = employeeRepository.findById(employeeId);
		if(emp.isPresent())return emp.get();
		else throw new EmployeeException(statusCode_NOT_FOUND + "Employee not found with the given ID.");
	}

	@Override
	public Employee updateEmployeeById(Employee employee, Integer employeeId) throws EmployeeException, DocumentException {
		Employee employeeWithId =  employeeRepository.findById(employeeId).orElseThrow( ()-> new EmployeeException(statusCode_NOT_FOUND + "No employee with such ID found."));

		if(employee.getDateOfBirth()!= null) employeeWithId.setDateOfBirth(employee.getDateOfBirth());
		if(employee.getDateOfJoining()!= null)employeeWithId.setDateOfJoining(employee.getDateOfJoining());
		if(employee.getDateOfLeaving()!= null)employeeWithId.setDateOfLeaving(employee.getDateOfLeaving());
	
		System.out.println("Check version 2");

		if(employee.getDocuments() != null && employee.getDocuments().size() != 0) {
			System.out.println("Check1");
			Integer intialLength = employeeWithId.getDocuments().size();

			for(Document newDocumentFromPatchEmployee : employee.getDocuments()) {
				
				Boolean same = false;
				Boolean validDocumentType = false;
				for(int i=0; i<intialLength; i++) {

//					equals and hashcode doesn't work for some reason					
//					if(employeeWithId.getDocuments().get(i).equals(newDocumentFromPatchEmployee)) {
//					if(employeeWithId.getDocuments().get(i).hashCode() == newDocumentFromPatchEmployee.hashCode()) {
					if(employeeWithId.getDocuments().get(i).getType().compareToIgnoreCase( newDocumentFromPatchEmployee.getType().toUpperCase()) == 0) {
						System.out.println("Check: "+employeeWithId.getDocuments().get(i).getType()+" - "+newDocumentFromPatchEmployee.getType().toUpperCase());

						same = true;
						break;
					}
				}
				if(!same) {
					
					for(DocumentType e : DocumentType.values()) {
						if(e.toString().compareToIgnoreCase(newDocumentFromPatchEmployee.getType()) == 0) {
							newDocumentFromPatchEmployee.setType(e.toString());
							validDocumentType = true;
						}
					}
					if(validDocumentType) {
					System.out.println("Check2");					
					employeeWithId.getDocuments().add(newDocumentFromPatchEmployee);
					}
					else throw new DocumentException("Document passed to update is of invalid document type. Please enter proper valid document type as the options provided.");
				}
				else {
					throw new DocumentException("Atleast one of the document type which you passed as input already exists.");
				}
			
			
				}
		}
		
		if(employee.getDepartment() != null) {

			String department = employee.getDepartment().toString().toUpperCase();
			if(department.compareToIgnoreCase("Development") == 0)employeeWithId.setDepartment(Department.Development.toString());
			else if(department.compareToIgnoreCase("QA") == 0)employeeWithId.setDepartment(Department.QA.toString());
			else if(department.compareToIgnoreCase("Admin") == 0)employeeWithId.setDepartment(Department.Admin.toString());
			else throw new EmployeeException("Incorrect department input");
		
		}
			

		if( employee.getEmailAddress() != null )employeeWithId.setEmailAddress(employee.getEmailAddress());
		if( employee.getFirstName() != null )employeeWithId.setFirstName(employee.getFirstName());
		if( employee.getLastName() != null )employeeWithId.setLastName(employee.getLastName());
		
		if(employee.getGender() != null) {
			
		String gender = employee.getGender().toString().toUpperCase();
		if(gender.compareToIgnoreCase("MALE") == 0)employeeWithId.setGender(Gender.MALE.toString());
		else if(gender.compareToIgnoreCase("FEMALE") == 0)employeeWithId.setGender(Gender.FEMALE.toString());
		else throw new EmployeeException("Incorrect gender input");
		
		}
		
		if( employee.getPhoneNumber() != null )employeeWithId.setPhoneNumber(employee.getPhoneNumber());

		return employeeRepository.save(employeeWithId);

	}


	@Override
	public List<Document> getAlldocumentsById(Integer employeeId) throws DocumentException, EmployeeException {

		Optional<Employee> employee = employeeRepository.findById(employeeId);
		
		if(employee.isPresent()) {
			List<Document> listOfDocuments = employee.get().getDocuments();
			if(listOfDocuments.size() == 0)throw new DocumentException(statusCode_NOT_FOUND+"Employee with Id "+ employeeId +" has has no documents in his records.");
			else return listOfDocuments;
		}
		else throw new EmployeeException(statusCode_NOT_FOUND + "Employee with Id "+ employeeId +" does not exists.");
		
	}


	@Override
	public List<Leaves> getAllLeavesByEmployeeIdWithinDateRange(Integer employeeID, DateRange dateRange) throws EmployeeException {

		LocalDate startDate = dateRange.getLeaveFrom();
		LocalDate endDate = dateRange.getLeaveTo();
		Optional<Employee> employee = employeeRepository.findById(employeeID);

		System.out.println("check version 2");
		if(employee.isEmpty())throw new EmployeeException(statusCode_NOT_FOUND+ "No employee found with ID " + employeeID);
		
		List<Leaves> listOfAllLeaves = leaveRepository.findAll();
		List<Leaves> listOfAllLeavesForAnEmployeeWithInDateRange = new ArrayList<>();
		
		for(Leaves leave : listOfAllLeaves) {
			
			if( leave.getEmployeeId() == employeeID ) {
								
				if(leave.getLeaveFrom().isEqual(startDate) || ( leave.getLeaveFrom().isAfter(startDate) && leave.getLeaveFrom().isBefore(endDate)) ||
					leave.getLeaveFrom().isEqual(endDate)	
					||
					leave.getLeaveTo().isEqual(startDate) || ( leave.getLeaveTo().isAfter(startDate) && leave.getLeaveTo().isBefore(endDate)) ||
					leave.getLeaveTo().isEqual(endDate)
					) 
				{
					listOfAllLeavesForAnEmployeeWithInDateRange.add(leave);
				}
			}
		}
		
		return listOfAllLeavesForAnEmployeeWithInDateRange;
	}

}
	
